package com.example.model.Search;


import com.example.model.Company.Company;
import com.example.model.Company.CompanyRepository;
import com.example.model.Job.CustomJobDao;
import com.example.model.Job.Job;
import com.example.model.Job.JobResponse;
import com.example.model.Specialization.Specialization;
import com.example.model.Specialization.SpecializationRepository;
import com.example.model.Tag.Tag;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    private GeoApiContext geoContext;

    @Autowired
    private CustomJobDao customJobDao;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Integer defaultRange;

    public SearchController() {
        defaultRange = 20;
    }

    @PostMapping(value = "/search/jobs")
    public ResponseEntity<List<JobResponse>> searchForJobs(@RequestBody SearchJobRequest request) {

        List<Job> foundJobs = customJobDao.buildAndExecuteSqlQuery(request);
        List<Job> selectedJobs = null;
        List<JobResponse> response;

        if(foundJobs == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if(foundJobs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if(request.getLocalization() != null) {
            if(!request.getLocalization().isEmpty()) {

                String origin = request.getLocalization();
                Integer searchRange;

                String[] destinations = generateDestinationsArray(foundJobs);
                DistanceMatrix googleMapsResponse = callGoogleMaps(origin, destinations);

                if (googleMapsResponse == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                if (request.getAreaRange() == null)
                    searchRange = defaultRange * 1000;
                else
                    searchRange = request.getAreaRange() * 1000;

                selectedJobs = new ArrayList<>();

                for (int i = 0; i < foundJobs.size(); i++) {
                    if (googleMapsResponse.rows[0].elements[i].distance.inMeters <= searchRange)
                        selectedJobs.add(foundJobs.get(i));
                }
            }
        }
        else {
            selectedJobs = foundJobs;
        }

        response = generateJobResponse(selectedJobs);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<JobResponse> generateJobResponse(List<Job> jobs) {

        List<JobResponse> response = new ArrayList<>();

        for(Job job : jobs) {

            List<Specialization> specs = specializationRepository.findAllByJob(job);
            List<Tag> jobTags = new ArrayList<>();

            for(Specialization spec : specs) {
               jobTags.add(spec.getTag());
            }

            response.add(new JobResponse(job, jobTags));
        }

        return response;
    }

    private String[] generateDestinationsArray(List<Job> destinations) {

        String[] destinationsArray = new String[destinations.size()];

        for(int i = 0; i < destinationsArray.length; i++) {
            destinationsArray[i] = destinations.get(i).getLocalization();
        }

        return destinationsArray;
    }

    private DistanceMatrix callGoogleMaps(String origin, String[] destinations) {

        DistanceMatrixApiRequest distanceRequest = new DistanceMatrixApiRequest(geoContext);
        distanceRequest.origins(origin);
        distanceRequest.destinations(destinations);

        DistanceMatrix distanceMatrix = null;

        try {
            distanceMatrix = distanceRequest.await();
        } catch (ApiException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return distanceMatrix;
    }



}
