package com.pelizzaris.sufs.service;

import com.pelizzaris.sufs.domain.dto.AlunoCreateDTO;
import com.pelizzaris.sufs.domain.dto.AlunoResponseDTO;
import com.pelizzaris.sufs.domain.model.Aluno;
import com.pelizzaris.sufs.mapper.AlunoMapper;
import com.pelizzaris.sufs.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    public AlunoResponseDTO salvar(AlunoCreateDTO dto) {
        Aluno aluno = alunoMapper.toEntity(dto);

        //criar validação de e-mail aqui
        aluno = alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(aluno);
    }
}
/**
 * @Service
 * @RequiredArgsConstructor // Injeção via construtor (Lombok)
 * public class AlunoService {
 *
 *     private final AlunoRepository repository;
 *     private final AlunoMapper mapper;
 *
 *     // --- CREATE (Já tínhamos) ---
 *     public AlunoResponseDTO criar(AlunoCreateDTO dto) {
 *         Aluno aluno = mapper.toEntity(dto);
 *         aluno = repository.save(aluno);
 *         return mapper.toResponseDTO(aluno);
 *     }
 *
 *     // --- FIND ALL (Listar Todos) ---
 *     public List<AlunoResponseDTO> listarTodos() {
 *         return repository.findAll() // Método padrão do JPA
 *                 .stream()
 *                 .map(mapper::toResponseDTO) // Converte cada Aluno em AlunoResponseDTO
 *                 .collect(Collectors.toList());
 *     }
 *
 *     // --- FIND BY ID (Buscar um) ---
 *     public AlunoResponseDTO buscarPorId(Long id) {
 *         Aluno aluno = repository.findById(id)
 *                 .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
 *         return mapper.toResponseDTO(aluno);
 *     }
 *
 *     // --- UPDATE (Atualizar) ---
 *     @Transactional
 *     public AlunoResponseDTO atualizar(Long id, AlunoUpdateDTO dto) {
 *         Aluno aluno = repository.findById(id)
 *                 .orElseThrow(() -> new RuntimeException("Aluno não encontrado!"));
 *
 *         // Mapper atualiza os campos do objeto 'aluno' com o que veio no 'dto'
 *         mapper.updateEntityFromDTO(dto, aluno);
 *
 *         // O save aqui funciona como update porque o objeto já tem ID
 *         aluno = repository.save(aluno);
 *
 *         return mapper.toResponseDTO(aluno);
 *     }
 *
 *     // --- DELETE (Remover) ---
 *     public void deletar(Long id) {
 *         if (!repository.existsById(id)) {
 *             throw new RuntimeException("Aluno não encontrado!");
 *         }
 *         repository.deleteById(id);
 *     }
 * }
 */
