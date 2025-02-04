package com.clientes.cadastro.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        // TODO: Personalizar a mensagem de erro retornada para o cliente, em vez de usar a implementação padrão.
        // Por exemplo, retornar uma lista de mensagens de validação para cada campo inválido.
        return super.handleMethodArgumentNotValid(ex, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<Object> handleConflict(DataIntegrityViolationException ex) {
        // TODO: Melhorar a mensagem de erro retornada para o cliente, fornecendo mais contexto sobre a violação de integridade.
        // Por exemplo, "O recurso não pode ser salvo devido a uma violação de integridade no banco de dados."
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    private ResponseEntity<Object> handleBadRequest(EmptyResultDataAccessException exception) {
        // TODO: Melhorar a mensagem de erro retornada para o cliente, explicando que o recurso solicitado não foi encontrado.
        // Por exemplo, "O recurso solicitado não foi encontrado no banco de dados."
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    // TODO: Adicionar um handler genérico para capturar exceções não tratadas e retornar uma mensagem amigável ao cliente.
    // Isso evita expor detalhes internos do sistema em caso de erros inesperados.
    // @ExceptionHandler(Exception.class)
    // private ResponseEntity<Object> handleGenericException(Exception ex) {
    //     return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado. Por favor, tente novamente mais tarde.");
    // }
}

//Resumo dos ajustes sugeridos:
//Personalizar mensagens de erro:
//
//No método handleMethodArgumentNotValid, em vez de usar a implementação padrão, retorne uma lista de mensagens de validação para cada campo inválido. Isso melhora a experiência do cliente ao fornecer informações claras sobre os erros.
//Nos métodos handleConflict e handleBadRequest, forneça mensagens mais amigáveis e explicativas para o cliente, evitando expor detalhes técnicos.
//Adicionar um handler genérico:
//
//Adicione um @ExceptionHandler genérico para capturar exceções não tratadas. Isso garante que erros inesperados sejam tratados de forma consistente e evita expor detalhes internos do sistema.
//Clean Code:
//
//Certifique-se de que as mensagens de erro sejam consistentes e claras.
//Evite expor mensagens de exceção diretamente para o cliente, pois elas podem conter informações sensíveis ou técnicas.
//
//Melhorar a experiência do cliente:
//
//Retorne mensagens de erro que sejam úteis e compreensíveis para o cliente final, evitando termos técnicos ou mensagens genéricas.
//Validação de campos:
//
//No caso de validações de argumentos inválidos (MethodArgumentNotValidException), considere retornar um objeto estruturado com os campos inválidos e suas respectivas mensagens de erro.
//Exemplo de personalização para handleMethodArgumentNotValid:
//
//
//
//
//@Override
//protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//    List<String> errors = ex.getBindingResult().getFieldErrors().stream()
//            .map(error -> error.getField() + ": " + error.getDefaultMessage())
//            .collect(Collectors.toList());
//    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errors);
//}