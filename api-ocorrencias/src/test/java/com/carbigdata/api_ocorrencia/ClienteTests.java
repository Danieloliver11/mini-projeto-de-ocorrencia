package com.carbigdata.api_ocorrencia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.carbigdata.api_ocorrencia.model.vo.ClienteVO;
import com.carbigdata.api_ocorrencia.service.ClienteService;

@SpringBootTest
@AutoConfigureMockMvc
class ClienteTests {
	@Mock
	private ClienteService clienteService;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Order(1)
	public void testCadastrarClient() {
		ClienteVO clienteVO = ClienteVO.builder().nome("cliente teste").cpf("52035101050")
				.dataNascimento(LocalDate.now()).senha("123").build();

		when(clienteService.cadastrarClient(any(ClienteVO.class))).thenReturn(clienteVO);

		ClienteVO response = clienteService.cadastrarClient(clienteVO);

		assertNotNull(response);
		assertEquals("cliente teste", response.nome());
		assertEquals("52035101050", response.cpf());

		verify(clienteService).cadastrarClient(clienteVO);
	}

	@Test
	@Order(2)
	public void testAtualizarClient() {
		ClienteVO clienteVO = ClienteVO.builder().id(1L).nome("cliente atualizado").cpf("52035101050")
				.dataNascimento(LocalDate.now()).senha("novaSenha").build();

		when(clienteService.atualizarClient(any(ClienteVO.class))).thenReturn(clienteVO);

		ClienteVO response = clienteService.atualizarClient(clienteVO);

		assertNotNull(response);
		assertEquals("cliente atualizado", response.nome());
		assertEquals("52035101050", response.cpf());

		verify(clienteService).atualizarClient(clienteVO);
	}

	@Test
	@Order(3)

	public void testRecuperarPorCpf() {
		String cpf = "52035101050";
		ClienteVO clienteVO = ClienteVO.builder().id(1L).nome("cliente teste").cpf(cpf).dataNascimento(LocalDate.now())
				.senha("123").build();

		when(clienteService.recuperarPorCpf(anyString())).thenReturn(clienteVO);

		ClienteVO response = clienteService.recuperarPorCpf(cpf);

		assertNotNull(response);
		assertEquals("cliente teste", response.nome());
		assertEquals(cpf, response.cpf());

		verify(clienteService).recuperarPorCpf(cpf);
	}

	@Test
	@Order(4)

	public void testDeletarCidadaoPorCpf() {
		String cpf = "52035101050";

		when(clienteService.recuperarPorCpf(anyString())).thenReturn(null);

		clienteService.deletarCidadao(cpf);

		verify(clienteService).deletarCidadao(cpf);
	}

}
