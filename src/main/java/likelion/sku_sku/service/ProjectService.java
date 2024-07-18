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
    public Project addProject(String classTh, String title, String subTitle, MultipartFile image) throws IOException {
        if (projectRepository.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("title 이미 있지롱");
        }
        byte[] imageBytes = image.getBytes();
        Project project = new Project(classTh, title, subTitle, imageBytes);
        return projectRepository.save(project);
    }

    @Transactional
    public Project updateProject(Long id, String classTh, String title, String subTitle, MultipartFile image) throws IOException {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("그런 id 가진 project 없지롱"));

        if (image != null) {
            project.setImage(image);
        }
        String newClassTh = (classTh != null ? classTh : project.getClassTh());
        String newTitle = (title != null ? title : project.getTitle());
        String newSubTitle = (subTitle != null ? subTitle : project.getSubTitle());
        project.changeProject(newClassTh, newTitle, newSubTitle);
        return project;
    }

    public List<Project> findProjectAll() {
        return projectRepository.findAll();
    }

    public ResponseProjectUpdate findProject(String title) {
        return projectRepository.findByTitle(title)
                .map(project -> new ResponseProjectUpdate(
                        project.getClassTh(),
                        project.getTitle(),
                        project.getSubTitle(),
                        project.arrayToImage()))
                .orElse(null);
    }

    @Transactional
    public void deleteProject(String title) {
        Project project = projectRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("그런 title 가진 project 없지롱" + title));
        projectRepository.delete(project);
    }
}
