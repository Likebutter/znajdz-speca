package com.example.model.Company;

import com.example.model.Search.SearchCompanyRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CustomCompanyDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomCompanyDao() {
    }

    public String buildSqlQuery(SearchCompanyRequest request) {

        Boolean firstCondition = true;
        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT company.* FROM company ");

        String tagCondition = checkForTags(request);
        if(!tagCondition.isEmpty()) {
            stringBuilder.append(tagCondition);
            firstCondition = false;
        }

        String nameCondition = checkForName(request, firstCondition);
        if(!nameCondition.isEmpty()) {
            stringBuilder.append(nameCondition);
            firstCondition = false;
        }

        String statsCondition = checkForStatistics(request, firstCondition);
        if(!statsCondition.isEmpty()) {
            stringBuilder.append(statsCondition);
            firstCondition = false;
        }

        return stringBuilder.toString();
    }

    public List<Company> executeSqlQuery(String sqlQuery) {

        Query query = entityManager.createNativeQuery(sqlQuery, Company.class);

        return query.getResultList();
    }

    public List<Company> buildAndExecuteSqlQuery(SearchCompanyRequest request) {

        String sqlQuery = buildSqlQuery(request);

        return executeSqlQuery(sqlQuery);
    }

    private String checkForTags(SearchCompanyRequest request) {

        StringBuilder builder = new StringBuilder();

        if(request.getTags() != null) {
            if(!request.getTags().isEmpty()) {
                builder.append("JOIN specialization ON company.id = specialization.company_id JOIN tag ON specialization.tag_id = tag.id WHERE (tag.name IN (");
                List<String> tags = request.getTags();

                for (int i = 0; i < tags.size(); i++) {
                    builder.append("'" + tags.get(i) + "'");

                    if (i < tags.size() - 1) {
                        builder.append(", ");
                    }
                }

                builder.append(")) ");
            }
        }

        return builder.toString();
    }

    private String checkForName(SearchCompanyRequest request, Boolean firstCondition) {

        StringBuilder builder = new StringBuilder();

        if(request.getName() != null) {
            if(!request.getName().isEmpty()) {
                if(!firstCondition)
                    builder.append("AND ");
                else
                    builder.append("WHERE ");

                builder.append("(LOWER(company.name) LIKE LOWER('%" + request.getName() + "%')) ");
            }
        }

        return builder.toString();
    }

    private String checkForStatistics(SearchCompanyRequest request, Boolean firstCondition) {

        StringBuilder builder = new StringBuilder();

        if(request.getRating() != null) {
            if(request.getRating() > 0.0f) {
                if(!firstCondition)
                    builder.append("AND ");
                else {
                    builder.append("WHERE ");
                    firstCondition = false;
                }

                builder.append("(company.rating >= " + request.getRating() + ")");
            }
        }

        if(request.getNumberJobs() != null) {
            if(request.getNumberJobs() > 0) {
                if(!firstCondition)
                    builder.append("AND ");
                else {
                    builder.append("WHERE ");
                    firstCondition = false;
                }

                builder.append("(company.number_jobs >= " + request.getNumberJobs() + ")");
            }
        }

        if(request.getNumberOpinions() != null) {
            if(request.getNumberOpinions() > 0) {
                if(!firstCondition)
                    builder.append("AND ");
                else {
                    builder.append("WHERE ");
                    firstCondition = false;
                }

                builder.append("(company.number_opinions >= " + request.getNumberOpinions() + ")");
            }
        }

        return builder.toString();
    }
}
