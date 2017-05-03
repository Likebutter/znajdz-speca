package com.example.model.Job;

import com.example.model.Search.SearchJobRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;
import java.util.*;

public class CustomJobDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, String> mappedNames;

    public CustomJobDao() {

        mappedNames = new HashMap<>();
        mappedNames.put("beginDate", "begin_date");
        mappedNames.put("endDate", "end_date");
    }

    public String buildSqlQuery(SearchJobRequest request) {

        Boolean checkedForTags = false;
        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT job.* FROM job ");

        String tagCondition = checkForTags(request);
        if(!tagCondition.isEmpty()) {
            stringBuilder.append(tagCondition);
            checkedForTags = true;
        }

        String dateCondition = checkForDates(request, checkedForTags);
        if(!dateCondition.isEmpty())
            stringBuilder.append(dateCondition);

        stringBuilder.append("AND (visible = true) ");

        return stringBuilder.toString();

    }

    public List<Job> executeSqlQuery(String sqlQuery) {

        Query query = entityManager.createNativeQuery(sqlQuery, Job.class);

        return query.getResultList();
    }

    public List<Job> buildAndExecuteSqlQuery(SearchJobRequest request) {

        String sqlQuery = buildSqlQuery(request);

        return executeSqlQuery(sqlQuery);
    }

    private String checkForDates(SearchJobRequest request, Boolean tags) {

        StringBuilder builder = new StringBuilder();

        if(tags) {
            builder.append("AND ");
        }
        else
            builder.append("WHERE ");

        if(request.getBeginDate() != null) {
            builder.append("(begin_date >= '" + request.getBeginDate() + "') ");

        }
        else {
            builder.append("(begin_date >= '" + new Date(Calendar.getInstance().getTime().getTime()) + "') ");
        }

        if( request.getEndDate() != null) {
            builder.append("AND (end_date <= '" + request.getEndDate() + "') ");
        }

        return builder.toString();
    }

    private String checkForTags(SearchJobRequest request) {

        StringBuilder builder = new StringBuilder();

        if(request.getTags() != null) {
            if(!request.getTags().isEmpty()) {
                builder.append("JOIN specialization ON job.id = specialization.job_id WHERE specialization.job_id IN (");
                List<String> tags = request.getTags();

                builder.append("SELECT job_id FROM specialization JOIN tag on specialization.tag_id = tag.id WHERE tag.name IN (");
                for (int i = 0; i < tags.size(); i++) {
                    builder.append("'" + tags.get(i) + "'");

                    if (i < tags.size() - 1) {
                        builder.append(", ");
                    }
                }

                builder.append(") GROUP BY job_id HAVING COUNT(job_id) = " + tags.size() + ") ");
            }
        }

        return builder.toString();
    }

}
