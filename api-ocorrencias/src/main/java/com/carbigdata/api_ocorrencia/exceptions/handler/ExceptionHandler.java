package com.carbigdata.api_ocorrencia.exceptions.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.carbigdata.api_ocorrencia.exceptions.DadosJaCadastradosException;
import com.carbigdata.api_ocorrencia.exceptions.MsgException;
import com.carbigdata.api_ocorrencia.exceptions.NaoAutorizadoException;
import com.carbigdata.api_ocorrencia.exceptions.NaoEncontradoException;
import com.carbigdata.api_ocorrencia.exceptions.ParametroInvalidoException;

import jakarta.servlet.http.HttpServletResponse;


@ControllerAdvice
public class ExceptionHandler  extends ResponseEntityExceptionHandler{
	

	
	private static final String HEADER_MESSAGE = "mensagem";

	private static final String TITLE_ERRO_REGRA_NEGOCIO = "Regra de negócio";
	private static final String TITLE_PARAMETROS_INVALIDOS = "Parâmetros inválidos";
	private static final String TITLE_DADOS_JA_CADASTRADOS = "Dados já cadastrados";
	private static final String TITLE_ERRO_SERVIDOR = "Erro no servidor";
	private static final String TYPE_VALIDACAO_AUTORIZACAO = "Não foi autorizado";
	private static final String ERRO_VALIDACAO = "Erro de validação";

	private static final String TYPE_VALIDACAO_REGRA_NEGOCIO = "Validação de regras de negócio";
	private static final String TYPE_VALIDACAO_PARAMETROS = "Validação de Parâmetros";
	private static final String TYPE_ERRO_INESPERADO = "Erro inesperado";
	private static final String TITLE_NAO_AUTORIZADO = "usuário não esta autorizado no sistema";
	private static final String TITLE_VALIDACAO_PARAMETRO = "Validação de Parâmetros";
	
	@org.springframework.web.bind.annotation.ExceptionHandler(MsgException.class)
	public ResponseEntity<Object> handleDadosJaCadastradosException(MsgException e, ServletWebRequest request) {
		logger.warn(e.getMessage());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_ERRO_REGRA_NEGOCIO,
				TYPE_VALIDACAO_REGRA_NEGOCIO, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, BAD_REQUEST, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ParametroInvalidoException.class)
	public ResponseEntity<Object> handleParametroInvalidoException(ParametroInvalidoException e, ServletWebRequest request) {
		logger.warn(e.getMessage().toUpperCase());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_PARAMETROS_INVALIDOS,
				TYPE_VALIDACAO_PARAMETROS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, BAD_REQUEST, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(DadosJaCadastradosException.class)
	public ResponseEntity<Object> handleDadosJaCadastradosException(DadosJaCadastradosException e, ServletWebRequest request) {
		logger.warn(e.getMessage().toUpperCase());

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_DADOS_JA_CADASTRADOS,
				TYPE_VALIDACAO_PARAMETROS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, BAD_REQUEST, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NaoEncontradoException.class)
	public ResponseEntity<Object> handleNaoEncontradoException(NaoEncontradoException e, ServletWebRequest request) {
		logger.warn(e.getMessage());
		
		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_DADOS_JA_CADASTRADOS,
				TYPE_VALIDACAO_PARAMETROS, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, NOT_FOUND, request);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaughtException(Exception e, ServletWebRequest request) {
		logger.error(e.getMessage(), e);

		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_ERRO_SERVIDOR, TYPE_ERRO_INESPERADO,
				Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, INTERNAL_SERVER_ERROR, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NaoAutorizadoException.class)
	public ResponseEntity<Object> handleNaoAutorizadoException(NaoAutorizadoException e, ServletWebRequest request) {
		logger.warn(e.getMessage());
		
		ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(TITLE_NAO_AUTORIZADO,
				TYPE_VALIDACAO_AUTORIZACAO, Arrays.asList(e.getMessage()), request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, bodyExceptionResponse, header, UNAUTHORIZED, request);
	}
	
	 @Override
	    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, 
	    		HttpStatusCode status, WebRequest request) {
	        
	        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .toList();

	        ExceptionResponseVO bodyExceptionResponse = criarExceptionResponse(ERRO_VALIDACAO,
	        		TITLE_VALIDACAO_PARAMETRO,
	                errors,
	                request.getDescription(false));

	        return handleExceptionInternal(ex, bodyExceptionResponse, headers, BAD_REQUEST, request);
	    }
	 

	private ExceptionResponseVO criarExceptionResponse(String title, String type, List<String> detail,
			String instance) {

		return ExceptionResponseVO
				.builder()
				.detail(detail)
				.instance(instance)
				.title(title)
				.type(type)
				.build();
	}
	
}
