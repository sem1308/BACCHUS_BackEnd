package uos.seclass.bacchus.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uos.seclass.bacchus.domains.CustomUserDetails;
import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.exceptions.ResourceNotFoundException;
import uos.seclass.bacchus.repositories.CustomerRepository;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

	private final CustomerRepository customerRepo;

	@Override
	public UserDetails loadUserByUsername(String id) {
		Customer customer = customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

		CustomUserDetails user = CustomUserDetails.builder().id(customer.getId()).pw(customer.getPw()).build();

		return user;
	}
}