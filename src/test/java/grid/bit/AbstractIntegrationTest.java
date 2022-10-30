package grid.bit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Persistable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.platform.commons.util.CollectionUtils.getOnlyElement;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@Rollback(value = false)
@SpringBootTest
@SqlConfig(encoding = "UTF-8")
public class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    protected final <T extends Persistable<?>, S> Optional<T> findByName(Class<T> beanClass, S name, EntityManager entityManager) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<T> criteriaBuilderQuery = criteriaBuilder.createQuery(beanClass);

        Root<T> root = criteriaBuilderQuery.from(beanClass);

        String nameColumnInGrid = "name";
        Predicate predicate = criteriaBuilder.equal(root.get(nameColumnInGrid), name);

        CriteriaQuery<T> criteriaQueryList = criteriaBuilderQuery
                .select(root)
                .where(predicate);

        int minAmountResult = 1;
        List<T> list = entityManager
                .createQuery(criteriaQueryList)
                .setMaxResults(minAmountResult)
                .getResultList();

        if (list.isEmpty()) {
            return Optional.empty();
        }
        Optional<T> onlyElement = Optional.of(getOnlyElement(list));

        return onlyElement;
    }

    protected final <ID, T extends Persistable<ID>> T find(Class<T> tClass, ID pk, EntityManager entityManager) {
        return entityManager.find(tClass, pk);
    }

    protected final <T extends Persistable<?>> T findOnly(Class<T> tClass, EntityManager entityManager) {
        List<T> list = findAll(tClass, entityManager);
        assertThat(list).hasSize(1);
        return list.get(0);
    }

    protected final <T> List<T> findAll(Class<T> tClass, EntityManager entityManager) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, null, entityManager);
        return allQuery.getResultList();
    }

    protected final <T extends Persistable<?>> T findFirst(Class<T> tClass , EntityManager entityManager) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, true, entityManager);
        List<T> list = allQuery.setMaxResults(1).getResultList();
        return list.isEmpty() ? null : getOnlyElement(list);
    }

    protected final <T extends Persistable<?>> T findLast(Class<T> tClass, EntityManager entityManager) {
        List<T> list = findLast(tClass, 1, entityManager);
        return list.isEmpty() ? null : getOnlyElement(list);
    }

    protected final <T extends Persistable<?>> List<T> findLast(Class<T> tClass, int count,EntityManager entityManager) {
        TypedQuery<T> allQuery = makeAllQuery(tClass, false, entityManager);
        return allQuery.setMaxResults(count).getResultList();
    }

    private <T> TypedQuery<T> makeAllQuery(Class<T> tClass, Boolean asc,EntityManager entityManager) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(tClass);
        Root<T> root = cq.from(tClass);
        CriteriaQuery<T> all = cq.select(root);
        if (asc != null) {
            all = all.orderBy(asc ? cb.asc(root.get("id")) : cb.desc(root.get("id")));
        }
        return entityManager.createQuery(all);
    }

    protected final <T extends Persistable<?>> long count(Class<T> tClass, EntityManager entityManager) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(tClass)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    protected final long count(String dbEntityName, EntityManager entityManager) {
        return (long) entityManager.createNativeQuery("SELECT COUNT(1) FROM " + dbEntityName).getSingleResult();
    }

    protected void flush(EntityManager entityManager) {
        entityManager.flush();
    }

    protected void flushAndClear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }

    protected String generateJsonForGridDto(String nameRandom, String cellSize) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{")
                .append("\"name\": ")
                .append("\"")
                .append(nameRandom)
                .append("\"")
                .append(',')
                .append("\"cellSize\": ")
                .append(cellSize)
                .append("}");

        return stringBuilder.toString();
    }
}
