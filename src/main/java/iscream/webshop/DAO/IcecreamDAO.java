package iscream.webshop.DAO;

import iscream.webshop.model.Icecream;
import iscream.webshop.repository.IcecreamRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IcecreamDAO {

    private final IcecreamRepository icecreamRepository;

    public  IcecreamDAO(IcecreamRepository icecreamRepository) {
        this.icecreamRepository = icecreamRepository;
    }

    public List<Icecream> getAllIcecream() {
        return icecreamRepository.findAll();
    }

    public Icecream addIcecream(final Icecream icecream) {
        return this.icecreamRepository.save(icecream);
    }

    public Icecream getIcecreamById(Long id) throws ChangeSetPersister.NotFoundException {
        Optional<Icecream> icecream = icecreamRepository.findById(id);
        if (icecream.isPresent()) {
            return icecream.get();
        }
        throw new ChangeSetPersister.NotFoundException();
    }

    public Icecream updateIcecream(Long id, Icecream newIcecream) throws ChangeSetPersister.NotFoundException {
        Optional<Icecream> oldIcecreamOptional = icecreamRepository.findById(id);
        if (oldIcecreamOptional.isPresent()) {
            Icecream oldIcecream = oldIcecreamOptional.get();

            newIcecream.setName((newIcecream.getName()) == null ? oldIcecream.getName() : newIcecream.getName());
            newIcecream.setDescription((newIcecream.getDescription()) == null ? oldIcecream.getDescription() : newIcecream.getDescription());
            newIcecream.setPrice((newIcecream.getPrice()) == 0.0f ? oldIcecream.getPrice() : newIcecream.getPrice());
            newIcecream.setImageURL((newIcecream.getImageURL()) == null ? oldIcecream.getImageURL() : newIcecream.getImageURL());

            icecreamRepository.setIcecream(
                    newIcecream.getName(),
                    newIcecream.getDescription(),
                    newIcecream.getPrice(),
                    newIcecream.getImageURL(),
                    newIcecream.getId()
            );

            return newIcecream;
        }
        throw  new ChangeSetPersister.NotFoundException();
    }

    public void deleteIcecreamById(Long id) throws ChangeSetPersister.NotFoundException {
        if (icecreamRepository.findById(id).isPresent()) {
            icecreamRepository.deleteIcecreamById(id);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }
}
