package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.anuppur.controller.CommonController;

import jakarta.servlet.http.HttpServletRequest;

class ErrorDisclosureAndPathValidationContractTest {

    private static final Path JAVA = Path.of("src", "main", "java");

    @Test
    void remarkIdentifiersAreStronglyTypedSoInvalidTextReachesGlobal400Handler() throws Exception {
        assertThat(CommonController.class.getMethod(
                "getAllDmRemarks", Long.class, HttpServletRequest.class)).isNotNull();
        assertThat(CommonController.class.getMethod(
                "getAllDepartmentRemarks", Long.class, HttpServletRequest.class)).isNotNull();
        assertThat(CommonController.class.getMethod(
                "getRemakrsDetails", Long.class, HttpServletRequest.class)).isNotNull();
        assertThat(CommonController.class.getMethod(
                "getDepartmentRemarksDetailsById", Long.class, HttpServletRequest.class)).isNotNull();
    }

    @Test
    void knownControllerAndServiceResponsesDoNotReturnRawExceptionMessages() throws Exception {
        for (Path relative : new Path[] {
                Path.of("com/anuppur/controller/CommonController.java"),
                Path.of("com/anuppur/controller/MobileApiController.java"),
                Path.of("com/anuppur/controller/WebserviceController.java"),
                Path.of("com/anuppur/service/impl/SystemAdminServiceImpl.java"),
                Path.of("com/anuppur/service/impl/CommonServiceImpl.java")
        }) {
            String source = Files.readString(JAVA.resolve(relative));
            assertThat(source)
                    .as(relative.toString())
                    .doesNotContain("setErrorMessage(e.getMessage())",
                            "body(e.getMessage())",
                            "return \"error: \" + e.getMessage()",
                            "return \"Error occurred while saving data: \" + e.getMessage()",
                            "setMessage(e.getMessage())",
                            "setError(e.getMessage())");
        }
    }
}
