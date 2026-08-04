package com.anuppur.security;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

/**
 * Rejects active content in fields which are defined as plain text by the
 * application. Values are never "cleaned" with a regular expression: invalid
 * input is rejected so callers cannot mistake a modified value for the value
 * that was submitted.
 */
@Component
public class UserSuppliedTextPolicy {

    private static final int DEFAULT_MAX_LENGTH = 500;
    private static final int REMARK_MAX_LENGTH = 4_000;
    private static final int MAX_OBJECT_DEPTH = 8;

    private static final Set<String> EXACT_PLAIN_TEXT_FIELDS = Set.of(
            "workname",
            "firstname",
            "lastname",
            "departmentname",
            "contractorname",
            "contractor",
            "name",
            "worktypename",
            "worktypenamee",
            "worktypenameh",
            "worksubtypename",
            "worksubtypenamee",
            "worksubtypenameh",
            "workcategoryname",
            "workcategorynamee",
            "workcategorynameh",
            "districtname",
            "districtnameh",
            "blockname",
            "blocknameh",
            "grampanchayatname",
            "grampanchayatnameh",
            "grampanchayatknameh",
            "implementationagencyname",
            "implementationagencynamee",
            "implementationagencynameh",
            "implagencyname",
            "subenggname",
            "workfacility",
            "workfacilityname",
            "facilityname",
            "departmentremarks",
            "dmremarks",
            "dmremakrs",
            "tsremarks",
            "asremarks",
            "handoverremarks",
            "handremarks",
            "workremarks",
            "remarks",
            "remark");

    public void validateParameter(String parameterName, String[] values) {
        String fieldName = leafName(parameterName);
        if (values == null) {
            return;
        }
        for (String value : values) {
            if (isPlainTextField(fieldName)) {
                validate(fieldName, value);
            } else {
                validateActiveSyntax(fieldName, value);
            }
        }
    }

    public void validateBody(Object body) {
        validateObject(body, new IdentityHashMap<>(), 0);
    }

    public void validate(String fieldName, String value) {
        if (value == null) {
            return;
        }

        int maxLength = isRemarkField(fieldName) ? REMARK_MAX_LENGTH : DEFAULT_MAX_LENGTH;
        if (value.length() > maxLength) {
            throw invalid(fieldName, "must not exceed " + maxLength + " characters");
        }

        validateActiveSyntax(fieldName, value);
    }

    private void validateActiveSyntax(String fieldName, String value) {
        if (value == null) {
            return;
        }
        String canonical = canonicalize(value);
        if (canonical.indexOf('\0') >= 0 || containsDisallowedControlCharacter(canonical)) {
            throw invalid(fieldName, "contains an invalid control character");
        }
        if (canonical.indexOf('<') >= 0 || canonical.indexOf('>') >= 0) {
            throw invalid(fieldName, "must be plain text; HTML markup is not allowed");
        }
        if (canonical.contains("{{") || canonical.contains("}}")) {
            throw invalid(fieldName, "must be plain text; template expressions are not allowed");
        }
    }

    private void validateObject(Object value, IdentityHashMap<Object, Boolean> visited, int depth) {
        if (value == null || depth > MAX_OBJECT_DEPTH) {
            return;
        }
        if (value instanceof String text) {
            validateActiveSyntax("request", text);
            return;
        }
        if (isSimpleType(value.getClass())) {
            return;
        }
        if (visited.put(value, Boolean.TRUE) != null) {
            return;
        }
        if (value instanceof Collection<?> collection) {
            collection.forEach(item -> validateObject(item, visited, depth + 1));
            return;
        }
        if (value instanceof Map<?, ?> map) {
            map.values().forEach(item -> validateObject(item, visited, depth + 1));
            return;
        }
        if (value.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(value); i++) {
                validateObject(Array.get(value, i), visited, depth + 1);
            }
            return;
        }
        if (!value.getClass().getPackageName().startsWith("com.anuppur")) {
            return;
        }

        for (Class<?> type = value.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.isSynthetic()) {
                    continue;
                }
                try {
                    field.setAccessible(true);
                    Object fieldValue = field.get(value);
                    if (fieldValue instanceof String text) {
                        if (isPlainTextField(field.getName())) {
                            validate(field.getName(), text);
                        } else {
                            validateActiveSyntax(field.getName(), text);
                        }
                    } else {
                        validateObject(fieldValue, visited, depth + 1);
                    }
                } catch (IllegalAccessException exception) {
                    throw new IllegalStateException("Unable to validate request field " + field.getName(), exception);
                }
            }
        }
    }

    private String canonicalize(String value) {
        String decoded = value;
        for (int i = 0; i < 3; i++) {
            String next = HtmlUtils.htmlUnescape(decoded);
            if (next.equals(decoded)) {
                break;
            }
            decoded = next;
        }
        return decoded;
    }

    private boolean containsDisallowedControlCharacter(String value) {
        return value.codePoints().anyMatch(codePoint ->
                Character.isISOControl(codePoint) && codePoint != '\r' && codePoint != '\n' && codePoint != '\t');
    }

    private boolean isPlainTextField(String fieldName) {
        String normalized = normalize(fieldName);
        return EXACT_PLAIN_TEXT_FIELDS.contains(normalized)
                || normalized.matches("remarks?\\d+");
    }

    private boolean isRemarkField(String fieldName) {
        String normalized = normalize(fieldName);
        return normalized.contains("remark");
    }

    private String leafName(String parameterName) {
        if (parameterName == null) {
            return "";
        }
        int dot = parameterName.lastIndexOf('.');
        String leaf = dot >= 0 ? parameterName.substring(dot + 1) : parameterName;
        return leaf.replaceAll("\\[\\d+\\]", "");
    }

    private String normalize(String fieldName) {
        return fieldName == null ? "" : fieldName.replaceAll("[^A-Za-z0-9]", "")
                .toLowerCase(Locale.ROOT);
    }

    private boolean isSimpleType(Class<?> type) {
        return type.isPrimitive()
                || Number.class.isAssignableFrom(type)
                || CharSequence.class.isAssignableFrom(type)
                || Boolean.class == type
                || Character.class == type
                || Enum.class.isAssignableFrom(type)
                || type.getPackageName().startsWith("java.time");
    }

    private InvalidPlainTextException invalid(String fieldName, String reason) {
        return new InvalidPlainTextException("Invalid " + fieldName + ": " + reason + ".");
    }
}
