package com.backend.trawisa.model.mapper;

import com.backend.trawisa.model.entity.*;
import com.backend.trawisa.model.enumtype.*;
import com.backend.trawisa.model.request.*;
import com.backend.trawisa.model.request.ko_round.KnockOutRoundRequest;
import com.backend.trawisa.model.request.ko_round.KoMainRoundSettingModel;
import com.backend.trawisa.model.request.ko_round.KoQualifyingRoundSettingModel;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TournamentMapper {



    TournamentEntity toTournamentEntity(CreateTournamentRequest request);

    @Named("toScratchRoundEntity")
    @Mapping(source = "request.tournamentFormat", target = "tournamentFormat", qualifiedByName = "mapToTournamentFormatEnum")
    @Mapping(source = "request.ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "request.ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "request.inputLevel", target = "inputLevel", qualifiedByName = "mapToDartInputLevelTypeEnum")
    @Mapping(target = "boardEntities", expression = "java(mapToTournamentBoardEntities(request.getBoardNo(), request.getDartType(), request.getTournamentFormat()))")
    @Mapping(target = "tournamentDateTimeEntities", expression = "java(mapToTournamentTimeTableEntities(request.getTournamentDateTime(), request.getTournamentFormat()))")
    @Mapping(source = "venue", target = "venueEntity")
    @Mapping(source = "tournament", target = "tournamentEntity")
    @Mapping(target = "id",ignore = true)
    ScratchRoundEntity toScratchRoundEntity(ScratchRoundRequest request,VenueEntity venue, TournamentEntity tournament);

    @Named("toEveryoneEntity")
    @Mapping(source = "request.tournamentFormat", target = "tournamentFormat", qualifiedByName = "mapToTournamentFormatEnum")
    @Mapping(source = "request.ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "request.ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "request.inputLevel", target = "inputLevel", qualifiedByName = "mapToDartInputLevelTypeEnum")
    @Mapping(source = "request.winningRule", target = "winningRule", qualifiedByName = "mapToDartWiningRule")
    @Mapping(source = "request.matchType", target = "matchType", qualifiedByName = "mapToDartMatchTypeEnum")
    @Mapping(target = "boardEntities", expression = "java(mapToTournamentBoardEntities(request.getBoardNo(), request.getDartType(), request.getTournamentFormat()))")
    @Mapping(target = "tournamentDateTimeEntities", expression = "java(mapToTournamentTimeTableEntities(request.getTournamentDateTime(), request.getTournamentFormat()))")
    @Mapping(source = "venue", target = "venueEntity")
    @Mapping(source = "tournament", target = "tournamentEntity")
    @Mapping(target = "id",ignore = true)
    EveryoneVsEveryoneEntity toEveryoneEntity(EveryoneVsEveryoneRequest request,VenueEntity venue, TournamentEntity tournament);

    @Named("toKORoundEntity")
    @Mapping(source = "request.tournamentFormat", target = "tournamentFormat", qualifiedByName = "mapToTournamentFormatEnum")
    @Mapping(target = "boardEntities", expression = "java(mapToTournamentBoardEntities(request.getBoardNo(), request.getDartType(), request.getTournamentFormat()))")
    @Mapping(target = "tournamentDateTimeEntities", expression = "java(mapToTournamentTimeTableEntities(request.getTournamentDateTime(), request.getTournamentFormat()))")
    @Mapping(source = "request.qualifyingRoundSetting", target = "koQualifyingSettingEntities",qualifiedByName = "toKOQualifyingSettingEntity")
    @Mapping(source = "request.mainRoundSetting", target = "koMainRoundEntities",qualifiedByName = "toKOMainRoundEntity")
    @Mapping(source = "venue", target = "venueEntity")
    @Mapping(source = "tournament", target = "tournamentEntity")
    @Mapping(target = "id",ignore = true)
    KORoundEntity toKORoundEntity(KnockOutRoundRequest request, VenueEntity venue, TournamentEntity tournament);


    @Mapping(source = "request.tournamentFormat", target = "tournamentFormat", qualifiedByName = "mapToTournamentFormatEnum")
    @Mapping(source = "request.ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "request.ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "request.inputLevel", target = "inputLevel", qualifiedByName = "mapToDartInputLevelTypeEnum")
    @Mapping(source = "request.winningRule", target = "winningRule", qualifiedByName = "mapToDartWiningRule")
    @Mapping(source = "request.matchType", target = "matchType", qualifiedByName = "mapToDartMatchTypeEnum")
    @Mapping(target = "boardEntities", expression = "java(mapToTournamentBoardEntities(request.getBoardNo(), request.getDartType(), request.getTournamentFormat()))")
    @Mapping(target = "tournamentDateTimeEntities", expression = "java(mapToTournamentTimeTableEntities(request.getTournamentDateTime(), request.getTournamentFormat()))")
    @Mapping(source = "venue", target = "venueEntity")
    @Mapping(source = "tournament", target = "tournamentEntity")
    @Mapping(target = "id",ignore = true)
    GroupStageRoundEntity toGroupStageRoundEntity(GroupStageRoundRequest request, TournamentEntity tournament, VenueEntity venue);


    @Named("toKOQualifyingSettingEntity")
    @Mapping(source = "ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "winningRule", target = "winningRule", qualifiedByName = "mapToDartWiningRule")
    @Mapping(source = "matchType", target = "matchType", qualifiedByName = "mapToDartMatchTypeEnum")
    KOQualifyingSettingEntity toKOQualifyingSettingEntity(KoQualifyingRoundSettingModel model);

    @Named("toKOMainRoundEntity")
    @Mapping(source = "ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "winningRule", target = "winningRule", qualifiedByName = "mapToDartWiningRule")
    @Mapping(source = "matchType", target = "matchType", qualifiedByName = "mapToDartMatchTypeEnum")
    KOMainRoundSettingEntity toKOMainRoundEntity(KoMainRoundSettingModel model);


    @Mapping(source = "isOneCount", target = "isOneCount")
    @Mapping(source = "isTeamSecondRound", target = "isTeamSecondRound")
    @Mapping(source = "oneVsThreeAndTwoVsFour", target = "oneVsThree_TwoVsFour")
    @Mapping(source = "oneVsThreeAndTwoVsFourAndSecondRound", target = "oneVsThree_TwoVsFour_SecondRound")
    @Mapping(source = "oneVsFourAndTwoVsThree", target = "oneVsFour_TwoVsThree")
    @Mapping(source = "oneVsFourAndTwoVsThreeAndSecondRound", target = "oneVsFour_TwoVsThree_SecondRound")
    TournamentSettingEntity toTournamentSettingEntity(CreateTournamentRequest request);


    @Mapping(source = "request.tournamentFormat", target = "tournamentFormat", qualifiedByName = "mapToTournamentFormatEnum")
    @Mapping(source = "request.ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "request.ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    @Mapping(source = "request.inputLevel", target = "inputLevel", qualifiedByName = "mapToDartInputLevelTypeEnum")
    @Mapping(target = "boardEntities", expression = "java(mapToTournamentBoardEntities(request.getBoardNo(), request.getDartType(), request.getTournamentFormat()))")
    @Mapping(target = "tournamentDateTimeEntities", expression = "java(mapToTournamentTimeTableEntities(request.getTournamentDateTime(), request.getTournamentFormat()))")
    @Mapping(source = "request.roundGroupSetting",  target = "roundGroupSetting",qualifiedByName = "mapToRoundGroupSettingEntity")
    @Mapping(source = "venue", target = "venueEntity")
    @Mapping(source = "tournament", target = "tournamentEntity")
    @Mapping(target = "id",ignore = true)
    RoundGroupEntity toRoundGroupEntity(RoundGroupRequest request,TournamentEntity tournament, VenueEntity venue);

    @AfterMapping
    default void toTournamentSetting(CreateTournamentRequest request, @MappingTarget TournamentEntity tournamentEntity) {
        if (request.getPlayerSystem().equalsIgnoreCase("DOUBLE")) {
            TournamentSettingEntity tournamentSetting = toTournamentSettingEntity(request);
            tournamentEntity.setTournamentSettingEntity(tournamentSetting);
        }
    }

    @Named("mapToDartRingInTypeEnum")
    default DartRingInType mapToDartRingInTypeEnum(Integer ringInType) {
        if (ringInType == null) {
            return null;
        }
        return DartRingInType.values()[ringInType];
    }

    @Named("mapToDartRingOutTypeEnum")
    default DartRingOutType mapToDartRingOutTypeEnum(Integer ringOutType) {
        if (ringOutType == null) {
            return null;
        }
        return DartRingOutType.values()[ringOutType];
    }

    @Named("mapToDartInputLevelTypeEnum")
    default DartInputLevelType mapToDartInputLevelTypeEnum(Integer inputLevel) {
        if (inputLevel == null) {
            return null;
        }
        return DartInputLevelType.values()[inputLevel];
    }

    @Named("mapToTournamentFormatEnum")
    default TournamentFormat mapToTournamentFormatEnum(Integer tournamentFormat) {
        if (tournamentFormat == null) {
            return null;
        }
        return TournamentFormat.values()[tournamentFormat];
    }

    @Named("mapToDartTypeEnum")
    default DartType mapToDartTypeEnum(Integer dartType) {
        if (dartType == null) {
            return null;
        }
        return DartType.values()[dartType];
    }

    @Named("mapToDartMatchTypeEnum")
    default DartMatchType mapToDartMatchTypeEnum(Integer matchType) {
        return DartMatchType.values()[matchType];
    }

    @Named("mapToDartWiningRule")
    default DartWinningRule mapToDartWiningRule(Integer winningRule) {
        return DartWinningRule.values()[winningRule];
    }



    @Named("mapToTournamentBoardEntities")
    default List<TournamentBoardEntity> mapToTournamentBoardEntities(List<Integer> boardNo, Integer dartType, Integer tournamentFormat) {
        return boardNo.stream()
                .map(board -> new TournamentBoardEntity(board, mapToDartTypeEnum(dartType), mapToTournamentFormatEnum(tournamentFormat)))
                .collect(Collectors.toList());
    }

    @Named("mapToTournamentTimeTableEntities")
    default List<TournamentDateTimeEntity> mapToTournamentTimeTableEntities(List<RoundDateTime> tournamentDateTime, Integer tournamentFormat) {
        return tournamentDateTime.stream()
                .map(koRoundDateTime -> new TournamentDateTimeEntity(koRoundDateTime.getMatchDate(),
                        koRoundDateTime.getMatchTime(), mapToTournamentFormatEnum(tournamentFormat)))
                .collect(Collectors.toList());
    }

    @Mapping(source = "ringInType", target = "ringInType", qualifiedByName = "mapToDartRingInTypeEnum")
    @Mapping(source = "ringOutType", target = "ringOutType", qualifiedByName = "mapToDartRingOutTypeEnum")
    RoundGroupSettingEntity mapToRoundGroupSettingEntity(RoundGroupSettingRequest request);

    @Named("mapToRoundGroupSettingEntity")
    List<RoundGroupSettingEntity> mapToRoundGroupSettingEntity(List<RoundGroupSettingRequest> groupSettingList);



}
