package vu.lt.usecases;

import lombok.Getter;
import lombok.Setter;
import vu.lt.entities.Candidate;
import vu.lt.entities.Interview;
import vu.lt.persistence.CandidatesDAO;
import vu.lt.persistence.InterviewDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Model
public class InterviewsForCandidates {

    @Inject
    private CandidatesDAO candidatesDAO ;

    @Inject
    private InterviewDAO interviewDAO;

    @Getter @Setter
    private Candidate candidate;

    @Getter @Setter
    private Interview interviewToCreate = new Interview();


    @Getter
    private List<Interview> allInterviews;


    @PostConstruct
    public void init() {
        loadAllInterviews();

        /*Map<String, String> requestParameters =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Integer candidateId = Integer.parseInt(requestParameters.get("candidateId"));
        this.candidate = candidatesDAO.findOne(candidateId);*/
    }

    /*@Transactional
    public String createInterview(){
        this.interviewDAO.persist(interviewToCreate);
        return "success";
    }*/

    @Transactional
    public String createInterview() {
        interviewToCreate.setCandidate(this.candidate);
        interviewDAO.persist(interviewToCreate);
        return "/interviews.xhtml?faces-redirect=true&candidateId=" + this.candidate.getId();
    }


    private void loadAllInterviews(){
        this.allInterviews = interviewDAO.loadAll();
    }
}