package com.anuppur.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

class StoredXssRenderingTest {

    private static final Path RESOURCES = Path.of("src", "main", "resources");

    @Test
    void affectedDataTablesUseBuiltInTextRenderer() throws IOException {
        List<Path> affectedTables = List.of(
                Path.of("static/angular/systemAdmin/SystemAdminController.js"),
                Path.of("templates/systemAdmin/manageWorkSubtype.html"),
                Path.of("templates/systemAdmin/manageWorkSubTypes.html"),
                Path.of("templates/systemAdmin/manageImplAgencyy.html"),
                Path.of("templates/systemAdmin/manageDistricts.html"),
                Path.of("templates/systemAdmin/manageBlock.html"),
                Path.of("templates/systemAdmin/manageGrampanchayat.html"),
                Path.of("templates/systemAdmin/manageDepartmentUser.html"),
                Path.of("templates/superAdmin/manageAgencyUsers.html"),
                Path.of("templates/superAdmin/manageImplAgency.html"),
                Path.of("templates/superAdmin/manageSubEngg.html"),
                Path.of("templates/common/work/editTender.html"));

        for (Path relativePath : affectedTables) {
            String source = Files.readString(RESOURCES.resolve(relativePath));
            assertThat(source)
                    .as("DataTable text renderer in %s", relativePath)
                    .contains("$.fn.dataTable.render.text()");
        }
    }

    @Test
    void affectedDisplayTemplatesUseNgBind() throws IOException {
        List<Path> displayTemplates = List.of(
                Path.of("templates/common/addWorkDataForm.html"),
                Path.of("templates/common/approveAndCreateWork.html"),
                Path.of("templates/common/manageOngoingWorks.html"),
                Path.of("templates/common/work/viewDmRemarks.html"),
                Path.of("templates/common/photoUpdateReport.html"),
                Path.of("templates/common/printForm.html"));

        for (Path relativePath : displayTemplates) {
            String source = Files.readString(RESOURCES.resolve(relativePath));
            assertThat(source)
                    .as("ng-bind output in %s", relativePath)
                    .contains("data-ng-bind=");
        }
    }

    @Test
    void worksGridsEncodeAuditedWorkAndRemarkColumns() throws IOException {
        String ongoing = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/manageOngoingWorks.html")));
        assertThat(ongoing)
                .contains("data: \"workName\", title: \"Work Name\", defaultContent: \"-\", render: $.fn.dataTable.render.text()")
                .contains("data: \"contractorName\", title: \"Contractor Name\", defaultContent: \"-\", render: $.fn.dataTable.render.text()")
                .contains("data: \"departmentRemarks\", title: \"Department Remarks\", defaultContent: \"-\", render: $.fn.dataTable.render.text()")
                .contains("const safeRemark = $.fn.dataTable.render.text().display(data || '-');")
                .doesNotContain("${data}</span>");

        String reports = Files.readString(RESOURCES.resolve(Path.of("templates/common/reports.html")));
        assertThat(reports)
                .contains("data: \"workName\", title: \"Work Name\", defaultContent: \"-\", render: $.fn.dataTable.render.text()")
                .contains("data: \"contractorName\", title: \"Contractor Name\", defaultContent: \"-\", render: $.fn.dataTable.render.text()")
                .contains("const dmRemark  = textRenderer.display(row.DmRemakrs || '-');");

        String yearWise = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/yearWiseReport.html")));
        assertThat(yearWise).contains("return $.fn.dataTable.render.text().display(contractorName);");
    }

    @Test
    void ongoingWorksActionsAreCompatibleWithStrictCsp() throws IOException {
        String ongoing = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/manageOngoingWorks.html")));

        assertThat(ongoing)
                .doesNotContain("onclick=", "href=\"javascript:", "href=\"#\"")
                .contains("data-work-action=\"open-financing-agency\"")
                .contains("data-work-action=\"open-financing-expenditure\"")
                .contains("data-work-action=\"open-dm-remark\"")
                .contains("data-work-action=\"open-progress-images\"")
                .contains(".on('click.manageOngoingWorksActions', '[data-work-action]'")
                .contains("href=\"#/viewWork/${data.id}\"");
    }

    @Test
    void ongoingWorksModalsUseTheRouteScopeInsteadOfUnscopedModalElements() throws IOException {
        String ongoing = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/manageOngoingWorks.html")));

        assertThat(ongoing)
                .contains("function getManageOngoingWorksScope()")
                .contains("document.getElementById('searchForm')")
                .contains("document.querySelector('[data-ng-view], [ng-view]')")
                .contains("function applyToManageOngoingWorksScope(scope, callback)")
                .doesNotContain("angular.element(document.getElementById('dmRemarkModal')).scope()")
                .doesNotContain("angular.element(document.getElementById('imageModal')).scope()");
    }

    @Test
    void editWorkDocumentActionsAreCompatibleWithStrictCsp() throws IOException {
        String editWork = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/editWork.html")));
        String editWorkTables = Files.readString(RESOURCES.resolve(
                Path.of("static/js/editWorkTables.js")));

        assertThat(editWork)
                .doesNotContain("onclick=")
                .contains("data-edit-ts-action")
                .contains("click.editWorkTsDocuments");
        assertThat(editWorkTables)
                .doesNotContain("onclick=")
                .contains("data-edit-work-action")
                .contains("download-progress-document")
                .contains("view-progress-document")
                .contains("click.editWorkDocuments");
    }

    @Test
    void departmentRemarkUploadShowsSecureValidationErrors() throws IOException {
        String controller = Files.readString(RESOURCES.resolve(
                Path.of("static/angular/common/CommonController.js")));
        String template = Files.readString(RESOURCES.resolve(
                Path.of("templates/common/work/viewDmRemarks.html")));

        assertThat(controller)
                .containsSubsequence(
                        "$scope.saveOrUpdateDepartmentRemarks = function(isValid, dmattachment)",
                        "var maxSizeUpload = 10 * 1024 * 1024;",
                        "$http.post('saveOrUpdateDepartment'",
                        "responsePromise.error(function(data, status)",
                        "data.error || data.errorMessage || data.message",
                        "alert(errorMessage);")
                .doesNotContain("formData.append(\"dmStatus\"");
        assertThat(template)
                .contains(".pdf,.jpg,.jpeg,.png,application/pdf,image/jpeg,image/png")
                .contains("PDF/JPG/PNG, max size 10 MB");
    }

    @Test
    void departmentRemarkListDoesNotRequestAnUndefinedEditId() throws IOException {
        String controller = Files.readString(RESOURCES.resolve(
                Path.of("static/angular/common/CommonController.js")));

        assertThat(controller).doesNotContain("$scope.getDepartmentRemarksDetails();");
    }

    @Test
    void manageUsersActionsAreCompatibleWithStrictCsp() throws IOException {
        String template = Files.readString(RESOURCES.resolve(
                Path.of("templates/superAdmin/manageUsers.html")));

        assertThat(template)
                .doesNotContain("onclick=\"return deleteUser(")
                .contains("data-user-action=\"delete\"")
                .contains("click.manageUsersActions");
    }
}
