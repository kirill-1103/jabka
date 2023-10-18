package sovcombank.jabka.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sovcombank.openapi.model.RequestOpenApi;
import sovcombank.jabka.userservice.exception.BadRequestException;
import sovcombank.jabka.userservice.exception.NotFoundException;
import sovcombank.jabka.userservice.mapper.RequestMapper;
import sovcombank.jabka.userservice.model.Request;
import sovcombank.jabka.userservice.repositories.RequestRepository;
import sovcombank.jabka.userservice.service.interfaces.RequestService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Override
    @Transactional
    public Optional<Request> addOne(RequestOpenApi requestOpenApi) {
        requestOpenApi.setId(null);
        Request request = requestMapper.toRequest(requestOpenApi);
        if (Objects.isNull(request)) {
            throw new BadRequestException();
        }
        Request requestFromDb = requestRepository.save(request);
        return Optional.of(requestFromDb);
    }

    @Override
    public Optional<Request> getOneById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Request> update(RequestOpenApi requestOpenApi) {
        if (Objects.isNull(requestOpenApi.getId())) {
            throw new BadRequestException("Id is null");
        }
        requestRepository.findById(requestOpenApi.getId())
                .orElseThrow(() -> new BadRequestException("Request with such id not exists"));
        Request request = requestMapper.toRequest(requestOpenApi);
        if (Objects.isNull(request)) {
            throw new BadRequestException();
        }
        request = requestRepository.save(request);
        return Optional.of(request);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("Id is null");
        }
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Request with such id is not exists"));
        requestRepository.delete(request);
    }

    @Override
    @Transactional
    public Optional<Request> byUserId(Long id){
        if(Objects.isNull(id)){
            throw new BadRequestException("Id is null");
        }
        Request request = requestRepository.findByUserId(id)
                .orElseThrow(()->new NotFoundException(String.format("User with such id not found. Id:%d",id)));
        return Optional.of(request);
    }
}
