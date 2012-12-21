package uk.ac.edu4all.dao.jpa;

import org.springframework.stereotype.Repository;

import uk.ac.edu4all.domain.Comment;

@Repository("CommentDao")
public class CommentDaoJpa extends GenericDaoJpa<Comment> {

}
