package org.plexobject.demo.services.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.plexobject.demo.services.controller.CustomerController;
import org.plexobject.demo.services.controller.OrderController;
import org.plexobject.demo.services.controller.PaymentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.plexobject.demo.services.controller.ProductController;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice(assignableTypes = {CustomerController.class, OrderController.class, PaymentController.class, ProductController.class})
public class GlobalControllerAdvice {
	/**
	 * Note use base class if you wish to leverage its handling.
	 * Some code will need changing.
	 */
	private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Problem> problem(final Throwable e) {
		UUID uuid = UUID.randomUUID();
		String logRef = uuid.toString();
		logger.error("logRef=" + logRef, e.getMessage(), e);
		return new ResponseEntity<Problem>(new Problem(logRef, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex
	) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
		String error;
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
			errors.add(error);
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
			errors.add(error);
		}
		ErrorMessage errorMessage = new ErrorMessage(errors);

		//Object result=ex.getBindingResult();//instead of above can allso pass the more detailed bindingResult
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleConstraintViolatedException(ConstraintViolationException ex
	) {
		Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();


		List<String> errors = new ArrayList<>(constraintViolations.size());
		String error;
		for (ConstraintViolation constraintViolation : constraintViolations) {

			error = constraintViolation.getMessage();
			errors.add(error);
		}

		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ProductInventoryNotAvailable.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleProductInventoryNotAvailable(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleProductNotFoundException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PaymentNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handlePaymentNotFoundException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleOrderNotFoundException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleValidationException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CreditLimitExceededException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleCreditLimitExceededException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CreditCardExpiredException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleCreditCardExpiredException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex
	) {

		List<String> errors = new ArrayList<>();
		String error = ex.getParameterName() + ", " + ex.getMessage();
		errors.add(error);
		ErrorMessage errorMessage = new ErrorMessage(errors);
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(code = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ResponseEntity<ErrorMessage> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex
	) {
		String unsupported = "Unsupported content type: " + ex.getContentType();
		String supported = "Supported content types: " + MediaType.toString(ex.getSupportedMediaTypes());
		ErrorMessage errorMessage = new ErrorMessage(unsupported, supported);
		return new ResponseEntity(errorMessage, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseEntity<ErrorMessage> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage());
		return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
	}

}