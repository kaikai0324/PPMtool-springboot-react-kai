package kai.ppmtool.services;

import kai.ppmtool.domain.Project;
import kai.ppmtool.exceptions.ProjectIdException;
import kai.ppmtool.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {

        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }
    }

    // postman get http://localhost:8080/api/project/IDxx
    public Project findProjectByIdentifier(String projectId){

        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null){
            throw new ProjectIdException("Project ID '"+projectId+"' does not exist");
        }
        return project;
    }

    // postman get http://localhost:8080/api/project/all
    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    // postman delete http://localhost:8080/api/project/IDXX
    public void deleteProjectByIdentifier(String projectid) {
        Project project = projectRepository.findByProjectIdentifier(projectid.toUpperCase());

        if (project == null) {
            throw new ProjectIdException("Cannot Project with ID '" + projectid + "'. This project does not exist");
        }

        projectRepository.delete(project);
    }

    // update: input id number on postman. Such as "id":1,
}