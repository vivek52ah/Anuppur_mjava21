package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.security.access.prepost.PreAuthorize;

import com.anuppur.controller.CommonController;
import com.anuppur.controller.MobileApiController;

class PendingBolaAuthorizationContractTest {

    @Test
    void webTenderProgressCompletionPiiAndDownloadEndpointsAreObjectScoped() {
        assertWorkGuarded(CommonController.class, List.of(
                "addWorkProgress", "addWorkProSubStatusUploading", "uploadedDrawingFiles",
                "addTendorWorkAgreement", "addContractorData", "getWorkTenderAgreement",
                "fetchContractorWorkDetails", "getContractorCountByWorkId",
                "fetchFullWorkDetails", "fetchTSASWorkDetails", "fetchWorkProgress",
                "downloadEntrepreneurDocument", "fetchCCWorkDetails", "downloadImageDocument",
                "downloadDocumentTS", "downloadDocumentTSRevised", "downloadDocumentWSPro",
                "downloadDocumentAS", "downloadDocumentDW", "downloadDocumentTender",
                "downloadDocumentProgress", "downloadDocumentCC", "addCCData",
                "downloadDocumentRemarks", "previewDocumentRemarks", "downloadDocumentAgreement",
                "downloadDocumentLOI", "downloadFile", "fetchWorkProgressDocumetnId",
                "fetchWorkStatusByWorkID", "fetchWorkStatusByWorkIDcc", "getGeoTaggingForWork",
                "fetchImagesByDateAndWorkId", "downloadDocumentsZip", "fetchFinancialAgency",
                "getFinancingAgencyList", "getFinancingAgencyExpenditureList"));
    }

    @Test
    void mobileTenderProgressSanctionAndDownloadEndpointsAreObjectScoped() {
        assertWorkGuarded(MobileApiController.class, List.of(
                "getWorkTenderAgreement", "downloadDocumentAS", "downloadDocumentTS",
                "fetchTSASWorkDetails", "fetchWorkProgress", "saveGeoTagingData",
                "getGeoTaggingForWork", "addWorkProgressMobileData",
                "addWorkProgressMoibleData", "fetchProgressImagesList",
                "downloadDocumentWSPro"));
    }

    private void assertWorkGuarded(Class<?> controller, List<String> methodNames) {
        for (String methodName : methodNames) {
            Method method = Arrays.stream(controller.getDeclaredMethods())
                    .filter(candidate -> candidate.getName().equals(methodName))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Missing method " + controller.getSimpleName()
                            + "." + methodName));
            PreAuthorize authorization = method.getAnnotation(PreAuthorize.class);
            assertThat(authorization)
                    .as(controller.getSimpleName() + "." + methodName + " authorization")
                    .isNotNull();
            assertThat(authorization.value())
                    .as(controller.getSimpleName() + "." + methodName + " object guard")
                    .containsAnyOf("@workAuthorization.canAccessWork",
                            "@workResourceAuthorization.canAccess");
        }
    }
}
