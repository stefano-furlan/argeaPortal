package com.argea.argeaportal.services;

import com.argea.argeaportal.database.ClienteCompanyClienteArgea;
import com.argea.argeaportal.database.ClienteCompanyClienteArgeaRepository;
import com.argea.argeaportal.database.clienteargea.ClienteArgea;
import com.argea.argeaportal.database.clienteargea.ClienteArgeaRepository;
import com.argea.argeaportal.database.clientecompany.*;
import com.argea.argeaportal.dto.ClienteArgeaDto;
import com.argea.argeaportal.rest.errorHandling.PortalErrorResponse;
import com.argea.argeaportal.rest.errorHandling.PortalOperationNotPermittedException;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteArgeaService {


    @Autowired
    ClienteArgeaRepository clienteArgeaRepository;

    @Autowired
    ClienteCompanyClienteArgeaRepository clienteCompanyClienteArgeaRepository;

    @Autowired
    ClienteCompanyRepository clienteCompanyRepository;

    @Autowired
    ClienteCompanyInfoRepository clienteCompanyInfoRepository;

    public Optional<ClienteArgeaDto> getClienteArgea(Integer idCliente) {
        Optional<ClienteArgea> clienteArgea = clienteArgeaRepository.findById(idCliente);

        if (!clienteArgea.isPresent()) {
            throw new PortalOperationNotPermittedException("specificare un codice cliente");
        }

        //MODELMAPPER è una libreria che rende semplice la gestione del mapping tra dto e oggetto
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setPreferNestedProperties(false);

        ClienteArgeaDto clienteArgeaDto = modelMapper.map(clienteArgea.get(), ClienteArgeaDto.class);
        clienteArgeaDto.setClientiCompany(new ArrayList<>());

        //FIXME: fare con vista
        List<ClienteCompanyClienteArgea> legami = clienteCompanyClienteArgeaRepository.findAllByCodiceClienteArgea(clienteArgea.get().getId());

        for (ClienteCompanyClienteArgea legame : legami) {
            ClienteCompanyId clienteCompanyId = new ClienteCompanyId();
            clienteCompanyId.setCompany(legame.getCompany());
            clienteCompanyId.setCodiceClienteCompany(legame.getCodiceClienteCompany());
            Optional<ClienteCompany> clienteCompany = clienteCompanyRepository.findById(clienteCompanyId);
            if (clienteCompany.isPresent()) {
                clienteArgeaDto.getClientiCompany().add(clienteCompany.get());
            }
        }

        return Optional.of(clienteArgeaDto);
    }

    public ClienteArgeaDto salvaClienteArgea(ClienteArgeaDto clienteArgeaDto) {

        //controlli di integrità//non devo poter salvare un cliente argea con stessa descrizione di uno diverso che esiste già
        List<ClienteArgea> clientiArgea = null;
        if (clienteArgeaDto.getId() == null) {
            clientiArgea = clienteArgeaRepository.findAllByDescrizioneIgnoreCase(clienteArgeaDto.getDescrizione());
        } else {
            clientiArgea = clienteArgeaRepository.findAllByDescrizioneIgnoreCaseAndIdNot(clienteArgeaDto.getDescrizione(), clienteArgeaDto.getId());
        }
        if (clientiArgea != null && !clientiArgea.isEmpty()) {
            //ERRORE!
            throw new PortalOperationNotPermittedException("Esiste già un cliente argea con questa descrizione");
        }


        ClienteArgea clienteArgea = new ClienteArgea();
        List<ClienteCompanyClienteArgea> legami = new ArrayList<>();
        if (clienteArgeaDto.getId() == null) {

        } else {
            clienteArgea = clienteArgeaRepository.findById(clienteArgeaDto.getId()).orElse(null);
            //TODO: sistemare i legami con hibernate: tutto questo codice è pedestre!
            legami = clienteCompanyClienteArgeaRepository.findAllByCodiceClienteArgea(clienteArgeaDto.getId());
            //TODO: levare questa parte quando si sistema hibernate
            //elimino tutti i legami e li ricreo...
            legami.clear();

            clienteCompanyClienteArgeaRepository.saveAll(legami);
        }

        //TODO: fare con modelmapper
/*        clienteArgea.setIntercompany(clienteArgeaDto.getIntercompany());
        clienteArgea.setDescrizione(clienteArgeaDto.getDescrizione());*/
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setPreferNestedProperties(false);
        clienteArgea = modelMapper.map(clienteArgeaDto, ClienteArgea.class);


        clienteArgea = clienteArgeaRepository.save(clienteArgea);

        for (ClienteCompany c : clienteArgeaDto.getClientiCompany()) {
            ClienteCompanyClienteArgea legame = new ClienteCompanyClienteArgea();
            legame.setCompany(c.getCompany());
            legame.setCodiceClienteCompany(c.getCodiceClienteCompany());
            legame.setCodiceClienteArgea(clienteArgea.getId());
            legami.add(legame);
        }
        clienteCompanyClienteArgeaRepository.saveAll(legami);
        clienteArgeaDto.setId(clienteArgea.getId());
        return clienteArgeaDto;
    }

    public void eliminaClienteArgea(ClienteArgeaDto clienteArgeaDto) {
        //controlli di integrità
        Optional<ClienteArgea> clienteArgea = clienteArgeaRepository.findById(clienteArgeaDto.getId());
        if (clienteArgea.isPresent()) {
            //TODO: fixme hibernate!
            List<ClienteCompanyClienteArgea> legami = clienteCompanyClienteArgeaRepository.findAllByCodiceClienteArgea(clienteArgea.get().getId());
            clienteCompanyClienteArgeaRepository.deleteAll(legami);
            clienteArgeaRepository.delete(clienteArgea.get());
        }
    }


    public Iterable<ClienteCompanyInfo> findAllClientiCompanyInfo() {
        return clienteCompanyInfoRepository.findAll();
    }

}
