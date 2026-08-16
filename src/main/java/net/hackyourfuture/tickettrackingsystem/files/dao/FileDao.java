package net.hackyourfuture.tickettrackingsystem.files.dao;

import java.util.List;
import java.util.UUID;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import net.hackyourfuture.tickettrackingsystem.files.model.FileModel;

@RegisterBeanMapper(FileModel.class)
public interface FileDao {

        @SqlQuery("""
                        INSERT INTO files (
                            ticket_id,
                            file_name,
                            storage_key,
                            content_type,
                            file_size
                        )
                        VALUES (
                            :ticketId,
                            :fileName,
                            :storageKey,
                            :contentType,
                            :fileSize
                        )
                        RETURNING *
                        """)
        FileModel create(
                        @Bind("ticketId") UUID ticketId,
                        @Bind("fileName") String fileName,
                        @Bind("storageKey") String storageKey,
                        @Bind("contentType") String contentType,
                        @Bind("fileSize") long fileSize);

        @SqlQuery("""
                        SELECT *
                        FROM files
                        WHERE file_id = :fileId
                        """)
        FileModel findById(
                        @Bind("fileId") UUID fileId);

        @SqlQuery("""
                        SELECT *
                        FROM files
                        WHERE ticket_id = :ticketId
                        ORDER BY created_at DESC
                        """)
        List<FileModel> findByTicketId(
                        @Bind("ticketId") UUID ticketId);

        @SqlUpdate("""
                        DELETE FROM files
                        WHERE file_id = :fileId
                        """)
        void delete(
                        @Bind("fileId") UUID fileId);
}