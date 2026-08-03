package com.anuppur.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.anuppur.bean.UserBean;
import com.anuppur.bean.WorkBean;

class UserSuppliedTextPolicyTest {

    private final UserSuppliedTextPolicy policy = new UserSuppliedTextPolicy();

    @Test
    void allowsNormalHindiAndEnglishPlainText() {
        assertDoesNotThrow(() -> policy.validate("workName", "सड़क निर्माण - Ward 12"));
        assertDoesNotThrow(() -> policy.validate("remarks", "Work complete.\nVerified by department."));
    }

    @Test
    void rejectsLiteralAndEncodedHtmlMarkup() {
        assertThrows(InvalidPlainTextException.class,
                () -> policy.validate("workName", "<img src=x onerror=alert(1)>"));
        assertThrows(InvalidPlainTextException.class,
                () -> policy.validate("departmentName", "&lt;script&gt;alert(1)&lt;/script&gt;"));
    }

    @Test
    void rejectsAngularTemplateExpressions() {
        assertThrows(InvalidPlainTextException.class,
                () -> policy.validate("contractorName", "{{constructor.constructor('alert(1)')()}}"));
    }

    @Test
    void validatesAffectedFieldsInsideJsonRequestBeans() {
        UserBean user = new UserBean();
        user.setFirstName("<svg onload=alert(1)>");
        assertThrows(InvalidPlainTextException.class, () -> policy.validateBody(user));

        WorkBean work = new WorkBean();
        work.setWorkName("Safe work");
        work.setDepartmentRemarks("<script>alert(1)</script>");
        assertThrows(InvalidPlainTextException.class, () -> policy.validateBody(work));
    }

    @Test
    void validatesMultipartAndFormParameters() {
        assertThrows(InvalidPlainTextException.class,
                () -> policy.validateParameter("remarks0", new String[] { "<b>unsafe</b>" }));
        assertDoesNotThrow(() ->
                policy.validateParameter("unrelatedNumericFilter", new String[] { "<not-a-save-field>" }));
    }
}
