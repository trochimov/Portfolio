package se.group.projektarbete.service;


import org.springframework.stereotype.Service;
import se.group.projektarbete.data.Issue;
import se.group.projektarbete.repository.IssueRepository;
import se.group.projektarbete.repository.WorkItemRepository;

import java.util.Optional;


@Service
public final class IssueService {

    private final IssueRepository issueRepository;
    private final WorkItemRepository workItemRepository;

    public IssueService(IssueRepository issueRepository, WorkItemRepository workItemRepository) {
        this.issueRepository = issueRepository;
        this.workItemRepository = workItemRepository;
    }

    public boolean updateIssue(Long id, Issue issue) {
        if (issueRepository.findById(id).isPresent()) {
            Optional<Issue> result = issueRepository.findById(id);
            result.get().setDescription(issue.getDescription());
            issueRepository.save(result.get());
            return true;
        }
        return false;
    }
}