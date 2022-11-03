package com.visionrent.exception;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.visionrent.exception.message.ApiResponseError;
@ControllerAdvice // merkezi exception handle etmek için @ControllerAdvice ekledim
public class VisionRentExceptionHandler  extends ResponseEntityExceptionHandler{
	
	// AMACIM : custom bir exception sistemini kurmak, gelebilecek exceptionları override ederek, istediğim yapıda
	// cevap verilmesini sağlıyorum
	
   private ResponseEntity<Object> buildResponseEntity(  ApiResponseError error) {
	  
	   return new ResponseEntity<>(error, error.getStatus());
	  
   }
	
	
	@ExceptionHandler(ResourceNotFoundException.class) // bu @ ile custom exceptionrımı yakalayacağım
	protected ResponseEntity<Object> handleResourceNotFoundException ( ResourceNotFoundException ex, WebRequest  request  )   {
																																																// WebRequest = gelen request
			ApiResponseError error =  new ApiResponseError( HttpStatus.NOT_FOUND, ex.getMessage(), request.getDescription(false)  );
	       
			/*
			 *  Map<String,String> map= new HashMap<>();
			 *  map.put("time", LocalDateTime.noe().toString());
		     *  map.put("message", ex.getMessage());
		     *  return new ResponseEntity<>(map,HttpStatus.CREATED);
			 */
				
			
			return buildResponseEntity(error);
			
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		    List<String> errors = ex.getBindingResult().getFieldErrors(). // bütün field errorlarını get ile aldım
		    																			stream().
		    																			map(e->e.getDefaultMessage()).//bütün errorların getMessage() metodunu alıyorum
		    																			collect(Collectors.toList());
		   
		    ApiResponseError error = new  ApiResponseError(HttpStatus.BAD_REQUEST,
		    																				errors.get(0).toString(),
		    																				request.getDescription(false));
		   
		    return buildResponseEntity(error);
		    		
	
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		// TODO return değeri düzeltilecek
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	
	
	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException ( RuntimeException ex, WebRequest  request  )   {
		
		ApiResponseError error =  new ApiResponseError( HttpStatus.INTERNAL_SERVER_ERROR,
																									ex.getMessage(),
																									request.getDescription(false)  );
		return buildResponseEntity(error);
}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleGeneralException ( Exception ex, WebRequest  request  )   {
		
		ApiResponseError error =  new ApiResponseError( HttpStatus.INTERNAL_SERVER_ERROR,
																								ex.getMessage(),
																								request.getDescription(false)  );
		return buildResponseEntity(error);
}
	
}