package app.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

public abstract class GenericAbstractDAO<T> {

        private Class<T> entity;
        protected EntityManager entityManager;

        public GenericAbstractDAO(Class<T> entity, EntityManager entityManager) {
            this.entity = entity;
            this.entityManager = entityManager;
        }

        protected EntityManager getEntityManager(){
            return entityManager;
        }

        protected abstract T findByID(int id);

        public void joinTransaction (){
            if(!entityManager.isJoinedToTransaction())entityManager.joinTransaction();
        }

        public void create(T entity) {
            getEntityManager().persist(entity);
        }

        public void update(T entity) {
            getEntityManager().merge(entity);
        }

        public void delete(T entity) {
            getEntityManager().remove(getEntityManager().merge(entity));
        }

        public T find(Object id) {
            return getEntityManager().find(entity, id);
        }

        public List<T> readAll() {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entity));
            return getEntityManager().createQuery(cq).getResultList();
        }

        public List<T> readRange(int rangeMin , int rangeMax) {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            cq.select(cq.from(entity));
            Query q = getEntityManager().createQuery(cq);
            q.setMaxResults(rangeMax - rangeMin + 1);
            q.setFirstResult(rangeMin);
            return q.getResultList();
        }

        public long count() {
            CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
            Root<T> rt = cq.from(entity);
            Expression<Long> longExpression = getEntityManager().getCriteriaBuilder().count(rt);
            cq.select(longExpression);
            Query q = getEntityManager().createQuery(cq);
            return (long)q.getSingleResult();
        }


}
