package gomes.luis.divisaodecontas.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class GenericService<T,K extends Serializable> {

    private final JpaRepository<T,K> repository;

    public GenericService(final JpaRepository<T,K> repository){
        this.repository = repository;
    }

    public List<T> buscarTodos(){
       List<T> todos = repository.findAll();
        lancaExcecaoSeEntidadesNaoExistirem(todos);
        return todos;
    }

    public Optional<T> buscarPorId(K id){
        Optional<T> entidade = repository.findById(id);
        lancaExcecaoSeEntidadeNaoExistir(id, entidade);
        return entidade;
    }

    public T salvar(final T objeto){
        return repository.save(objeto);
    }

    public T atualizar(final K id, final T objeto){
        validaSeExiste(id);
        return repository.save(objeto);
    }

    public void excluirPorId(K id) {
        validaSeExiste(id);
        repository.deleteById(id);
    }

    private void validaSeExiste(K id) {
        Optional<T> t = buscarPorId(id);
        lancaExcecaoSeEntidadeNaoExistir(id, t);
    }

    private void lancaExcecaoSeEntidadesNaoExistirem(List<T> todos) {
        if (todos.isEmpty())
            throw new EntityNotFoundException("Entidades inexistentes.");
    }

    private void lancaExcecaoSeEntidadeNaoExistir(K id, Optional<T> entidade) {
        if (entidade.isEmpty())
            throw new EntityNotFoundException("Entidade com id:" + id + " Não Existe.");
    }

}
