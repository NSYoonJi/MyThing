package com.project.mything.perfume.repository;

import com.project.mything.perfume.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByIdIn(Long[] id);
}
