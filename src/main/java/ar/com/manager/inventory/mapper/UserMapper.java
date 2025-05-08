package ar.com.manager.inventory.mapper;

import ar.com.manager.inventory.dto.SaleDto;
import ar.com.manager.inventory.dto.UserDto;
import ar.com.manager.inventory.entity.Sale;
import ar.com.manager.inventory.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    SaleMapper saleMapper;
    public UserMapper(SaleMapper saleMapper) {
        this.saleMapper = saleMapper;
    }
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        List<SaleDto> sales = new ArrayList<>();
        userDto.setId(user.getId());
         userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUserName(user.getUserName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setType(user.getType());
        userDto.setEnabled(user.isEnabled());
        userDto.setSales(sales);
        if(user.getSales() != null ) {
            for(Sale sale : user.getSales()){
                userDto.addSale(saleMapper.toDto(sale));
            }
        }
        return userDto;
    }
    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setEmail(userDto.getEmail());
        user.setType(userDto.getType());
        user.setEnabled(userDto.isEnabled());
        return user;
    }
}
