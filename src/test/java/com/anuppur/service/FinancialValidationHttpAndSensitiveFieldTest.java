package com.anuppur.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import com.anuppur.entity.Work;
import com.anuppur.exception.FinancialValidationException;
import com.anuppur.exception.FinancialValidationExceptionHandler;
import com.anuppur.repository.ImplAgencyRepository;
import com.anuppur.repository.WorkRepository;

class FinancialValidationHttpAndSensitiveFieldTest {

    @Test
    void financialValidationReturnsCleanHttp400Body() {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/systemAdmin/saveExpense");
        FinancialValidationException exception = new FinancialValidationException("Amount cannot be negative.");

        ResponseEntity<java.util.Map<String, Object>> response =
                new FinancialValidationExceptionHandler().handleFinancialValidation(exception, request);

        assertThat(response.getStatusCode().value()).isEqualTo(400);
        assertThat(response.getBody()).containsEntry("error", "Bad Request");
        assertThat(response.getBody()).containsEntry("message", "Amount cannot be negative.");
    }

    @Test
    void normalEditCannotChangeImplementingAgency() {
        Work work = new Work();
        work.setId(42L);
        work.setImplementationAgency(5L);
        WorkSensitiveFieldService service = new WorkSensitiveFieldService(
                mock(WorkRepository.class), mock(ImplAgencyRepository.class));

        assertThatThrownBy(() -> service.validateNormalEditImplementationAgency(work, 6L))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("cannot be changed through the normal work edit");
    }

    @Test
    void normalEditMayResubmitSameImplementingAgency() {
        Work work = new Work();
        work.setId(42L);
        work.setImplementationAgency(5L);
        WorkSensitiveFieldService service = new WorkSensitiveFieldService(
                mock(WorkRepository.class), mock(ImplAgencyRepository.class));

        service.validateNormalEditImplementationAgency(work, 5L);
    }
}
