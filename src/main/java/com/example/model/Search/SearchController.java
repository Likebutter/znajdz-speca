package com.example.model.Search;


import com.example.model.Company.Company;
import com.example.model.Company.CompanyRepository;
import com.example.model.Company.CompanyResponse;
import com.example.model.Company.CustomCompanyDao;
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
import com.google.maps.model.DistanceMatrixElementStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
    private CustomCompanyDao customCompanyDao;

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
        Boolean localizationCheck = false;

        if(foundJobs == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if(foundJobs.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if(request.getLocalization() != null) {
            if(!request.getLocalization().isEmpty()) {

                String origin = request.getLocalization();
                Integer searchRange;

                String[] destinations = generateJobsDestinationsArray(foundJobs);
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
                    if(googleMapsResponse.rows[0].elements[i].status == DistanceMatrixElementStatus.OK)
                        if (googleMapsResponse.rows[0].elements[i].distance.inMeters <= searchRange)
                            selectedJobs.add(foundJobs.get(i));
                }

                localizationCheck = true;
            }
        }

        if(!localizationCheck) {
            selectedJobs = foundJobs;
        }

        Integer total = selectedJobs.size();

        if(!selectedJobs.isEmpty()) {
            List<Integer> indices = getPageIndices(selectedJobs, request.getPageNumber());
                selectedJobs = selectedJobs.subList(indices.get(0), indices.get(1));
        }

        MultiValueMap<String, String> headerContents = new LinkedMultiValueMap<>();
        headerContents.add("Total", total.toString());
        response = generateJobResponse(selectedJobs);
        return new ResponseEntity<>(response, headerContents, HttpStatus.OK);
    }

    @PostMapping(value = "/search/company")
    public ResponseEntity<List<CompanyResponse>> searchForCompanies(@RequestBody SearchCompanyRequest request) {

        List<Company> foundCompanies = customCompanyDao.buildAndExecuteSqlQuery(request);
        List<Company> selectedCompanies = null;
        List<CompanyResponse> response;
        Boolean localizationCheck = false;

        if (foundCompanies == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        if (foundCompanies.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (request.getLocalization() != null) {
            if (!request.getLocalization().isEmpty()) {

                String origin = request.getLocalization();
                Integer searchRange;

                String[] destinations = generateCompaniesDestinationsArray(foundCompanies);
                DistanceMatrix googleMapsResponse = callGoogleMaps(origin, destinations);

                if (googleMapsResponse == null) {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                if (request.getAreaRange() == null)
                    searchRange = defaultRange * 1000;
                else
                    searchRange = request.getAreaRange() * 1000;

                selectedCompanies = new ArrayList<>();

                for (int i = 0; i < foundCompanies.size(); i++) {
                    if(googleMapsResponse.rows[0].elements[i].status == DistanceMatrixElementStatus.OK)
                        if (googleMapsResponse.rows[0].elements[i].distance.inMeters <= searchRange)
                            selectedCompanies.add(foundCompanies.get(i));
                }
                localizationCheck = true;
            }
        }

        if (!localizationCheck){
            selectedCompanies = foundCompanies;
        }

        Integer total = selectedCompanies.size();

        if(!selectedCompanies.isEmpty()) {
            List<Integer> indices = getPageIndices(selectedCompanies, request.getPageNumber());
                selectedCompanies = selectedCompanies.subList(indices.get(0), indices.get(1));
        }

        MultiValueMap<String, String> headerContents = new LinkedMultiValueMap<>();
        headerContents.add("Total", total.toString());
        response = generateCompanyResponse(selectedCompanies);

        return new ResponseEntity<>(response, headerContents, HttpStatus.OK);
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

    private List<CompanyResponse> generateCompanyResponse(List<Company> companies) {

        List<CompanyResponse> response = new ArrayList<>();

        for(Company company : companies) {

            List<Specialization> specs = specializationRepository.findAllByCompany(company);
            List<Tag> companyTags = new ArrayList<>();

            for(Specialization spec : specs) {
                companyTags.add(spec.getTag());
            }

            response.add(new CompanyResponse(company, companyTags));
        }

        return response;
    }

    private String[] generateJobsDestinationsArray(List<Job> destinations) {

        String[] destinationsArray = new String[destinations.size()];

        for(int i = 0; i < destinationsArray.length; i++) {
            destinationsArray[i] = destinations.get(i).getLocalization();
        }

        return destinationsArray;
    }

    private String[] generateCompaniesDestinationsArray(List<Company> destinations) {

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

    private List<Integer> getPageIndices(List<?> list, Integer pageNumber) {

        Integer numberPages = new Double((Math.ceil(list.size()/10))).intValue();
        Integer startIndex;
        Integer endIndex;

        if( (pageNumber > numberPages) || (pageNumber < 0) )
            pageNumber = numberPages;

        if(pageNumber != 0) {

            startIndex = 10 * (pageNumber-1);
            if(pageNumber.equals(numberPages))
                endIndex = list.size();
            else
                endIndex = startIndex + 10;
        }
        else {

            if((list.size() % 10) == 0)
                startIndex = list.size() - 10;
            else
                startIndex = list.size() - (list.size() % 10);

            endIndex = list.size();
        }

        List<Integer> indices = new ArrayList<>();
        indices.add(startIndex);
        indices.add(endIndex);

        return indices;
    }
}
