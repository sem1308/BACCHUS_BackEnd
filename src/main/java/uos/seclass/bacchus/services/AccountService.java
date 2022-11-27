package uos.seclass.bacchus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uos.seclass.bacchus.domains.Account;
import uos.seclass.bacchus.dtos.UpdateAccountDTO;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.mappers.AccountMapper;
import uos.seclass.bacchus.repositories.AccountRepository;
import uos.seclass.bacchus.repositories.CustomerRepository;

@Service
public class AccountService {
    private  final AccountRepository accountRepo;

    @Autowired
    public AccountService(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    public void pay(String cusName, String cardNum, int price){
        Account account = accountRepo.findByCardNumAndOwnerName(cardNum,cusName)
                .orElseThrow(() -> new ResourceNotFoundException("카드 정보가 없습니다."));

        UpdateAccountDTO dto = new UpdateAccountDTO();
        if (account.getMoney() >= price){
            dto.setMoney(account.getMoney() - price);
            AccountMapper.INSTANCE.updateFromDto(dto, account);
            accountRepo.save(account);
        }else{
            throw new ResourceNotFoundException("잔액이 부족합니다.");
        }
    }

    public void refund(String cusName, String cardNum, int price){

        Account account = accountRepo.findByCardNumAndOwnerName(cardNum,cusName)
                .orElseThrow(() -> new ResourceNotFoundException("카드 정보가 없습니다."));

        UpdateAccountDTO dto = new UpdateAccountDTO();
        dto.setMoney(account.getMoney() + price);
        AccountMapper.INSTANCE.updateFromDto(dto, account);
        accountRepo.save(account);
    }

    public void checkDuplicate(String cardNum,String cusName){
        if (accountRepo.findByCardNumAndOwnerName(cardNum,cusName).isEmpty()){
            throw new ResourceNotFoundException("카드 정보가 없습니다.");
        }
    }
}

