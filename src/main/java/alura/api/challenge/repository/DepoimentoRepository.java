package alura.api.challenge.repository;

import alura.api.challenge.domain.model.Depoimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepoimentoRepository extends JpaRepository<Depoimento, Long> {
    Page<Depoimento> findAllByAtivoTrue(Pageable pageable);

    @Query(value = "SELECT * FROM depoimentos WHERE ativo = true ORDER BY RANDOM() LIMIT 3", nativeQuery = true)
    List<Depoimento> buscarDepoimentosAleatorios();

    Depoimento getReferenceByIdAndAtivoTrue(Long id);
}
