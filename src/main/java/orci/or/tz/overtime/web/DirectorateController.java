package orci.or.tz.overtime.web;


import orci.or.tz.overtime.dto.directorate.DirectorateRequestDto;
import orci.or.tz.overtime.dto.directorate.DirectorateResponseDto;
import orci.or.tz.overtime.exceptions.ResourceNotFoundException;
import orci.or.tz.overtime.models.Directorate;
import orci.or.tz.overtime.services.DirectorateService;
import orci.or.tz.overtime.utilities.Commons;
import orci.or.tz.overtime.utilities.GenericResponse;
import orci.or.tz.overtime.utilities.LoggedUser;
import orci.or.tz.overtime.utilities.Mapper;
import orci.or.tz.overtime.web.api.DirectorateApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DirectorateController implements DirectorateApi {

    @Autowired
    private DirectorateService directorateService;

    @Autowired
    private Commons commons;

    @Autowired
    private Mapper mapper;

    @Autowired
    private LoggedUser loggedUser;


    @Override
    public ResponseEntity<DirectorateResponseDto> CreateDirectorate(DirectorateRequestDto request) {
        Directorate d = new Directorate();
        d.setDirectorateName(request.getDirectorateName());
        d.setCreatedDate(LocalDateTime.now());
        d.setCreatedBy(loggedUser.getInfo().getId());
        directorateService.SaveDirectorate(d);

        DirectorateResponseDto resp = commons.GenerateDirectorate(d);
        return ResponseEntity.ok(resp);

    }

    @Override
    public ResponseEntity<DirectorateResponseDto> GetDirectorateById(Long id) throws ResourceNotFoundException {
        Optional<Directorate> d = directorateService.GetDirectorateById(id);

        if (!d.isPresent()) {
            throw new ResourceNotFoundException("Directorate with provided ID [" + id + "] does not exist.");
        }

        DirectorateResponseDto resp = commons.GenerateDirectorate(d.get());
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity<GenericResponse<List<DirectorateResponseDto>>> GetAllDirectorates(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Directorate> d = directorateService.GetAllDirectorates(pageRequest);
        ModelMapper modelMapper = mapper.getModelMapper();
        List<DirectorateResponseDto> directorates = d.stream().map(v -> modelMapper.map(v, DirectorateResponseDto.class)).collect(Collectors.toList());

        // setting the DirectoratePaginationResponseDto fields
        GenericResponse<List<DirectorateResponseDto>> response = new GenericResponse<>();
        response.setCurrentPage(page);
        response.setPageSize(size);
        Integer totalCount = directorateService.countTotalItems();
        response.setTotalItems(totalCount);
        response.setTotalPages(commons.GetTotalNumberOfPages(totalCount, size));
        response.setData(directorates);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<DirectorateResponseDto> UpdateDirectorate(DirectorateRequestDto request, Long id) throws ResourceNotFoundException {
        Optional<Directorate> d = directorateService.GetDirectorateById(id);

        if (!d.isPresent()) {
            throw new ResourceNotFoundException("Directorate with provided ID [" + id + "] does not exist.");
        }

        Directorate dr = d.get();
        dr.setDirectorateName(request.getDirectorateName());
        dr.setLastModifiedDate(LocalDateTime.now());
        dr.setLastModifiedBy(loggedUser.getInfo().getId());
        directorateService.SaveDirectorate(dr);

        DirectorateResponseDto resp = commons.GenerateDirectorate(dr);
        return ResponseEntity.ok(resp);
    }

    @Override
    public ResponseEntity <Integer>  CountDirectorates() {
        int total = directorateService.countTotalItems();
        return ResponseEntity.ok(total);
    }
}
