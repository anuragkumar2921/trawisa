package com.backend.trawisa.repositories;

import com.backend.trawisa.model.entity.TournamentEntity;
import com.backend.trawisa.model.entity.VenueEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TournamentRepo extends JpaRepository<TournamentEntity,Integer> {

    Optional<TournamentEntity> findByTournamentName(String tournamentName);

    @Query("SELECT venue FROM VenueEntity venue WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(venue.latitude)) * cos(radians(venue.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(venue.latitude)))) <= :radius")
    List<VenueEntity> findLocationsWithinRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);


   /* @Query("SELECT tournament FROM TournamentEntity tournament WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(venue.latitude)) * cos(radians(venue.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(venue.latitude)))) <= :radius")
    List<TournamentEntity> findTournamentInRadius(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("radius") double radius);
*/

}
