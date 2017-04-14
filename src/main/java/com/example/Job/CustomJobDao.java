package com.example.Job;

import com.example.Search.SearchJobRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

public class CustomJobDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CustomJobDao() {

    }

    public String buildSqlQuery(SearchJobRequest request) {

        Boolean checkedForTags = false;
        StringBuilder stringBuilder = new StringBuilder("SELECT * FROM job ");

        String tagCondition = checkForTags(request);
        if(!tagCondition.isEmpty()) {
            stringBuilder.append(tagCondition);
            checkedForTags = true;
        }

        String dateCondition = checkForDates(request, checkedForTags);
        if(!dateCondition.isEmpty())
            stringBuilder.append(dateCondition);

        String localizationCondition = checkForLocalization(request);
        if(!localizationCondition.isEmpty())
            stringBuilder.append(localizationCondition);

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
            builder.append("(begin_date > '" + request.getBeginDate() + "') ");

        }
        else {
            builder.append("(begin_date > '" + new Date(Calendar.getInstance().getTime().getTime()) + "') ");
        }

        if( request.getEndDate() != null) {
            builder.append("AND (end_date <= '" + request.getEndDate() + "') ");
        }

        return builder.toString();
    }

    private String checkForLocalization(SearchJobRequest request) {

        StringBuilder builder = new StringBuilder();

        if(request.getLocalizations() != null) {
            if(!request.getLocalizations().isEmpty()) {

                builder.append("AND (localization IN (");
                List<String> localizations = request.getLocalizations();

                for(int i = 0; i < localizations.size(); i++) {
                    builder.append("'" + localizations.get(i) + "'");

                    if(i < (localizations.size() - 1)) {
                        builder.append(", ");
                    }
                }

                builder.append(")) ");
            }
        }

        return builder.toString();
    }

    private String checkForTags(SearchJobRequest request) {

        StringBuilder builder = new StringBuilder();

        if(request.getTags() != null) {
            builder.append("JOIN specialization ON job.id = specialization.job_id JOIN tag ON specialization.tag_id = tag.id ");
            builder.append("WHERE (tag.name IN (");
            List<String> tags = request.getTags();

            for(int i = 0; i < tags.size(); i++) {
                builder.append("'" + tags.get(i) + "'");

                if(i < tags.size()-1) {
                    builder.append(", ");
                }
            }

            builder.append(")) ");
        }

        return builder.toString();
    }

}
