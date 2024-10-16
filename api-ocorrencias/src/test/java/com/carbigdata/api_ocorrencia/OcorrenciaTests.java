package com.carbigdata.api_ocorrencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.carbigdata.api_ocorrencia.controller.OcorrenciaController;
import com.carbigdata.api_ocorrencia.model.entity.ClienteEntity;
import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.enumeration.RoleEnum;
import com.carbigdata.api_ocorrencia.model.enumeration.StatusOcorrenciaEnum;
import com.carbigdata.api_ocorrencia.model.vo.EnderecoVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaResponseVO;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaVO;
import com.carbigdata.api_ocorrencia.security.AuthService;
import com.carbigdata.api_ocorrencia.service.ClienteService;
import com.carbigdata.api_ocorrencia.service.OcorrenciaService;

@SpringBootTest
@AutoConfigureMockMvc
class OcorrenciaTests {
	@Mock
	private ClienteService clienteService;
	
	 @Mock
	    private OcorrenciaService ocorrenciaService;

	    @Mock
	    private AuthService authService;

	    @InjectMocks
	    private OcorrenciaController ocorrenciaController;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	   @Test
	    @Order(1)
	    public void testSalvarOcorrencia() throws Exception {
	        OcorrenciaVO ocorrenciaVO = OcorrenciaVO
	                .builder()
	                .cpf("52035101050")
	                .nome("cliente teste")
	                .endereco(EnderecoVO.builder()
	                        .bairro("teste")
	                        .cep("123454")
	                        .locradouro("teste")
	                        .nomeCidade("teste")
	                        .nomeEstado("teste")
	                        .build())
	                .build();

	        when(ocorrenciaService.salvarOcorrencia(any(OcorrenciaVO.class))).thenReturn(ocorrenciaVO);

	        OcorrenciaVO response = ocorrenciaService.salvarOcorrencia(ocorrenciaVO);

	        assertNotNull(response);
	        assertEquals("cliente teste", response.nome());
	        assertEquals("52035101050", response.cpf());

	        verify(ocorrenciaService).salvarOcorrencia(ocorrenciaVO);

	    
	    }
	    

	    @Test
	    @Order(2)
	    public void testAtualizarOcorrencia() {
	        OcorrenciaVO ocorrenciaVO = OcorrenciaVO
	                .builder()
	                .id(1L)
	                .cpf("52035101050")
	                .nome("cliente teste")
	                .endereco(EnderecoVO.builder()
	                        .bairro("teste")
	                        .cep("123454")
	                        .locradouro("teste")
	                        .nomeCidade("teste")
	                        .nomeEstado("teste")
	                        .build())
	                .build();

	        ClienteEntity cliente = ClienteEntity.builder()
	                .id(1L)
	                .nome("cliente teste")
	                .cpf("52035101050")
	                .build();

	        OcorrenciaEntity ocorrenciaEntity = new OcorrenciaEntity();
	        ocorrenciaEntity.setId(1L);
	        ocorrenciaEntity.setCliente(cliente);

	        when(clienteService.recuperarClientePorNomeCpf(any(String.class), any(String.class))).thenReturn(cliente);
	        when(ocorrenciaService.recuperarOcorrenciaPorId(1L)).thenReturn(ocorrenciaEntity);
	        when(ocorrenciaService.atualizarOcorrencia(any(OcorrenciaVO.class))).thenReturn(ocorrenciaVO);

	        OcorrenciaVO response = ocorrenciaService.atualizarOcorrencia(ocorrenciaVO);

	        assertNotNull(response);
	        assertEquals("cliente teste", response.nome());
	        assertEquals("52035101050", response.cpf());

	        verify(ocorrenciaService).atualizarOcorrencia(ocorrenciaVO);
	    }
	    
	    @Test
	    @Order(3)
	    public void testFinalizarOcorrenciaPorId() {
	        Long ocorrenciaId = 1L; 
	        OcorrenciaResponseVO ocorrenciaResponseVO = OcorrenciaResponseVO.builder()
	                .id(ocorrenciaId)
	                .status(StatusOcorrenciaEnum.FINALIZADO)
	                .build();

	        when(ocorrenciaService.finalizarOcorrenciaPorId(ocorrenciaId)).thenReturn(ocorrenciaResponseVO);
	        
	        doNothing().when(authService).verificarAutorizacaoRoles(RoleEnum.ROLE_ADM);

	        OcorrenciaResponseVO response = ocorrenciaService.finalizarOcorrenciaPorId(ocorrenciaId);

	        assertNotNull(response);
	        assertEquals(ocorrenciaId, response.id());
	        assertEquals(StatusOcorrenciaEnum.FINALIZADO, response.status());

	        verify(ocorrenciaService).finalizarOcorrenciaPorId(ocorrenciaId);
	    }
	    
	    
}
