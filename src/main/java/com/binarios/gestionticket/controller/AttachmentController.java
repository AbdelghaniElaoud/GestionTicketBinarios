package com.binarios.gestionticket.controller;

import com.binarios.gestionticket.service.AttachmentService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attachment")
@AllArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;


    //Add an attachment to a ticket
    @PostMapping("/addToTicket/{ticketId}")
    @PreAuthorize("hasAnyAuthority('TECH', 'CLIENT')")
    public ResponseEntity<String> fileStoringHandler(@RequestParam("file") MultipartFile file, @PathVariable("ticketId") Long ticketId) throws Exception {

        attachmentService.addFileToTicket(file, ticketId);

        return new ResponseEntity<>("The file has been saved", HttpStatus.CREATED);
    }

    //Delete an attachment of a ticket -There is a parallel deletion from the attachment DB, and the list of the Ticket-

    @DeleteMapping("/delete/{attachmentId}")
    @PreAuthorize("hasAnyAuthority('CLIENT','ADMIN','TECH')")
    public ResponseEntity<String> deleteAttachment(@PathVariable("attachmentId") Long attachmentId) throws Exception {
        attachmentService.deleteAttachmentById(attachmentId);
        return new ResponseEntity<>("The attachment with the id " + attachmentId + " has been deleted successfully", HttpStatus.OK);
    }

}
