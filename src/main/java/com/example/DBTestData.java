package com.example;


import com.example.Client.Client;
import com.example.Client.ClientRepository;
import com.example.Company.Company;
import com.example.Company.CompanyRepository;
import com.example.Job.Job;
import com.example.Job.JobRepository;
import com.example.Specialization.Specialization;
import com.example.Specialization.SpecializationRepository;
import com.example.Tag.Tag;
import com.example.Tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Component
public class DBTestData implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
    public void run(String... args) throws Exception {

        Client client = new Client();
        client.setName("Jan");
        client.setLastname("Kowalski");
        client.setEmail("jankowalski@example.com");
        client.setPassword("12345678");
        client.setPhoneNumber("123456789");
        clientRepository.save(client);

        Company company = new Company();
        company.setName("Kowalscy");
        company.setAreaRange(25);
        company.setLocalization("Warszawa");
        company.setEmail("kowalscy@example.com");
        company.setPassword("12345678");
        companyRepository.save(company);

        Character name = 'a';
        int tagsNumber = 15;
        int jobsNumber = 20;
        int localsNumber = 15;
        String[] localizations = new String[localsNumber];
        fillLocalsTable(localizations);

        for(int i = 0; i < tagsNumber; i++) {

            Tag tag = new Tag();
            tag.setName(name.toString());
            tagRepository.save(tag);
            name++;
        }

        Date[][] beginDateTable = fillDateTable(null);

        int bdtFirstIndex = beginDateTable.length;
        int bdtSecondIndex = beginDateTable[0].length;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDateTable[bdtFirstIndex-1][bdtSecondIndex-1]);
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        Date[][] endDateTable = fillDateTable(new Date(calendar.getTime().getTime()));
        calendar = Calendar.getInstance();

        Random random = new Random();

        for(int i = 0; i < jobsNumber; i++) {
            Job job = new Job();
            job.setClient(client);
            job.setVisible(true);
            job.setBeginDate(getRandomDate(beginDateTable));
            job.setEndDate(getRandomDate(endDateTable));
            job.setAddedAt(new Date(calendar.getTime().getTime()));
            job.setLocalization(localizations[random.nextInt(15)]);
            jobRepository.save(job);
        }

        List<Job> jobs = jobRepository.findAll();

        for(int i = 0; i < jobsNumber; i++) {

            Job job = jobs.get(0);
            jobs.remove(0);
            int tagsInJob = 1 + random.nextInt(3);

            for(int j = 0; j < tagsInJob; j++) {
                Tag tag = tagRepository.findById(1 + random.nextInt(tagsNumber));
                Specialization specialization = new Specialization(tag, job);
                specializationRepository.save(specialization);
            }
        }

    }

    private Date[][] fillDateTable(Date startDate) {

        Date[][] dateTable = new Date[4][7];
        Calendar calendar = Calendar.getInstance();
        Date currentDate;

        if(startDate == null) {
            currentDate = new Date(calendar.getTime().getTime());
        }
        else {
            currentDate = startDate;
            calendar.setTime(startDate);
        }

        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 7; j++) {
                dateTable[i][j] = currentDate;
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = new Date(calendar.getTimeInMillis());
            }
        }

        return dateTable;
    }

    private Date getRandomDate(Date[][] dateTable) {

        Random random = new Random();
        int week = random.nextInt(4);
        int day = random.nextInt(7);

        return dateTable[week][day];
    }

    private void fillLocalsTable(String[] localsTable) {

        localsTable[0] = "Warszawa";
        localsTable[1] = "Plock";
        localsTable[2] = "Gdynia";
        localsTable[3] = "Sopot";
        localsTable[4] = "Katowice";
        localsTable[5] = "Zakopane";
        localsTable[6] = "Wroclaw";
        localsTable[7] = "Lublin";
        localsTable[8] = "Zamosc";
        localsTable[9] = "Szczecin";
        localsTable[10] = "Gdansk";
        localsTable[11] = "Torun";
        localsTable[12] = "Pruszkow";
        localsTable[13] = "Kielce";
        localsTable[14] = "Zabrze";
    }

}
