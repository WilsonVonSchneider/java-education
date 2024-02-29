package com.setronica.eventing.persistence.Tickets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {
}