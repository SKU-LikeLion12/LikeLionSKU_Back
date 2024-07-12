package likelion.sku_sku.service;

import likelion.sku_sku.domain.Project;
import likelion.sku_sku.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static likelion.sku_sku.dto.ProjectDTO.ResponseProjectUpdate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Transactional
    public Project addProject(String title, String subTitle, MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        Project project = new Project(title, subTitle, imageBytes);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, String title, String subTitle, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id " + id));

        if (image != null) {
            project.setImage(image);
        }
        String newTitle = (title != null ? title : project.getTitle());
        String newSubTitle = (subTitle != null ? subTitle : project.getSubTitle());
        project.changeProject(newTitle, newSubTitle);
        return project;
    }

    public List<Project> findProjectAll() {
        return projectRepository.findAll();
    }

    public ResponseProjectUpdate findProject(String title) {
        return projectRepository.findByTitle(title)
                .map(project -> new ResponseProjectUpdate(
                        project.getTitle(),
                        project.getSubTitle(),
                        project.arrayToImage()))
                .orElse(null);
    }
    @Transactional
    public void deleteProject(String title) {
        Project project = projectRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Project not found with title " + title));
        projectRepository.delete(project);
    }

}
