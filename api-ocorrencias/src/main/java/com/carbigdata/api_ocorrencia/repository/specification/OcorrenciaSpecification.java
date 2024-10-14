package com.carbigdata.api_ocorrencia.repository.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.carbigdata.api_ocorrencia.model.entity.OcorrenciaEntity;
import com.carbigdata.api_ocorrencia.model.vo.OcorrenciaFiltroVO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OcorrenciaSpecification implements Specification<OcorrenciaEntity> {

	private static final long serialVersionUID = 1L;
	
	private final OcorrenciaFiltroVO filtro;
	private final boolean asc;
	private final boolean isAdm;
	private final Long idUsuarioLogado;


	
	public OcorrenciaSpecification(OcorrenciaFiltroVO filtro, boolean asc, boolean isAdm, Long idUsuarioLogado){
		this.filtro = filtro;
		this.asc = asc;
		this.isAdm = isAdm;
		this.idUsuarioLogado = idUsuarioLogado;

		
	}

	@Override
	public Predicate toPredicate(Root<OcorrenciaEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		List<Predicate> condicoes = new ArrayList<>();
		
		if (asc) {
			query.orderBy(criteriaBuilder.asc(root.get("dataOcorrencia")),
					criteriaBuilder.asc(root.get("endereco").get("nomeCidade")));
		} else {
			query.orderBy(criteriaBuilder.desc(root.get("dataOcorrencia")),
					criteriaBuilder.desc(root.get("endereco").get("nomeCidade")));

		}
		
		if(!isAdm) {
			Predicate condicao = criteriaBuilder.equal(root.get("cliente").get("id"), idUsuarioLogado);
			condicoes.add(condicao);
			
		}

		
		if (filtro.isPossuiNome()) {
			Predicate condicao = criteriaBuilder.like(root.get("cliente").get("nome"), filtro.getNome());
			condicoes.add(condicao);
		}
		
		if (filtro.isPossuiCpf()) {	
			Predicate condicao = criteriaBuilder.like(root.get("cliente").get("cpf"), filtro.getCpf());
			condicoes.add(condicao);
		}
		
		if (filtro.isPossuiCidade()) {
			Predicate condicao = criteriaBuilder.like(root.get("endereco").get("nomeCidade"), filtro.getCidade());
			condicoes.add(condicao);
		}
		
		if (filtro.isPossuiData()) {
			Predicate condicao = criteriaBuilder.equal(root.get("dataOcorrencia"), filtro.getData());
			condicoes.add(condicao);
		}
		
		
		return criteriaBuilder.and(condicoes.toArray(new Predicate[0]));
	}

}
