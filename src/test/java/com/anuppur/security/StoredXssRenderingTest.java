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
}
