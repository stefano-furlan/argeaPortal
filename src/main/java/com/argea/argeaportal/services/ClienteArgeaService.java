package com.argea.argeaportal.services;

import com.argea.argeaportal.database.ClienteCompanyClienteArgea;
import com.argea.argeaportal.database.ClienteCompanyClienteArgeaRepository;
import com.argea.argeaportal.database.clienteargea.ClienteArgea;
import com.argea.argeaportal.database.clienteargea.ClienteArgeaRepository;
import com.argea.argeaportal.database.clientecompany.*;
import com.argea.argeaportal.dto.ClienteArgeaDto;
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

        if(!clienteArgea.isPresent()){
            //FIXME: creare eccezione e gestire a livello generale
            return null;
        }

        ClienteArgeaDto clienteArgeaDto = new ClienteArgeaDto();
        clienteArgeaDto.setId(clienteArgea.get().getId());
        clienteArgeaDto.setDescrizione(clienteArgea.get().getDescrizione());
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
        //TODO:


        ClienteArgea clienteArgea;
        List<ClienteCompanyClienteArgea> legami = new ArrayList<>();
        if (clienteArgeaDto.getId() == null) {
            clienteArgea = new ClienteArgea();
        } else {
            clienteArgea = clienteArgeaRepository.findById(clienteArgeaDto.getId()).orElse(null);
            //TODO: sistemare i legami con hibernate: tutto questo codice è pedestre!
            legami = clienteCompanyClienteArgeaRepository.findAllByCodiceClienteArgea(clienteArgeaDto.getId());
            //TODO: levare questa parte quando si sistema hibernate
            //elimino tutti i legami e li ricreo...
            legami.clear();
            clienteCompanyClienteArgeaRepository.saveAll(legami);
        }

        clienteArgea.setDescrizione(clienteArgeaDto.getDescrizione());
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

    public void eliminaClienteArgea(ClienteArgeaDto clienteArgeaDto){
        //controlli di integrità
      Optional<ClienteArgea> clienteArgea=  clienteArgeaRepository.findById(clienteArgeaDto.getId());
        if(clienteArgea.isPresent()){
            clienteArgeaRepository.delete(clienteArgea.get());
        }
    }


    public Iterable<ClienteCompanyInfo> findAllClientiCompanyInfo() {
        return clienteCompanyInfoRepository.findAll();
    }

}
