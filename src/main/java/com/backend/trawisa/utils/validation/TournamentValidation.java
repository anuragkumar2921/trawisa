package com.backend.trawisa.utils.validation;

import com.app.base.project.utils.MultiLangMessage;
import com.backend.trawisa.exception.ApiValidationException;
import com.backend.trawisa.model.request.CreateTournamentRequest;
import com.backend.trawisa.model.request.EveryoneVsEveryoneRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.app.base.project.utils.Utils.isNullOrEmpty;

@Component
public class TournamentValidation {

    private final ApiValidationException validationException;
    private final MultiLangMessage langMessage;

    private final ValidationUtils validationUtils;

    @Autowired
    public TournamentValidation(ApiValidationException validationException, MultiLangMessage langMessage, ValidationUtils validationUtils) {
        this.validationException = validationException;
        this.langMessage = langMessage;
        this.validationUtils = validationUtils;
    }


    public boolean isCreateTournamentReqValid(CreateTournamentRequest request) throws ApiValidationException {

        if (isNullOrEmpty(request.getTournamentName())) {
            validationException.setErrorMessage("Tournament name is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getDartType())) {
            validationException.setErrorMessage("Dart type is required");
            throw validationException;
        } else if (request.getIsSeries() == null) {
            validationException.setErrorMessage("isSeries is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getTournamentType())) {
            validationException.setErrorMessage("Tournament type is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getSeriesName())) {
            validationException.setErrorMessage("Series name is required");
            throw validationException;
        } else if (request.getTotalTeams() == null) {
            validationException.setErrorMessage("Total Team is required");
            throw validationException;
        } else if (isNullOrEmpty(request.getPlayerSystem())) {
            validationException.setErrorMessage("Player system is required");
            throw validationException;
        } else if (request.getIsQualifyingRound() == null) {
            validationException.setErrorMessage("isQualifyingRound is required");
            throw validationException;
        } else if (request.getTotalQualifyingTeam() == null) {
            validationException.setErrorMessage("TotalQualifyingTeam is required");
            throw validationException;
        } else if (request.getIsLeagueLimit() == null) {
            validationException.setErrorMessage("isLeagueLimit is required");
            throw validationException;
        } else if (request.getIsClass() == null) {
            validationException.setErrorMessage("isClass is required");
            throw validationException;
        } else if (request.getIsDistance() == null) {
            validationException.setErrorMessage("isDistance is required");
            throw validationException;
        }


        return true;
    }

    public boolean isEveryoneVsEveryoneReqValid(EveryoneVsEveryoneRequest request) throws ApiValidationException {
        if (request.getTournamentId() == null) {
            validationException.setErrorMessage("tournamentId is required");
            throw validationException;
        } else if (request.getTournamentFormat() == null) {
            validationException.setErrorMessage("tournamentFormat is required");
            throw validationException;
        } else if (request.getIsSecondRound() == null) {
            validationException.setErrorMessage("isSecondRound is required");
            throw validationException;
        } else if (request.getTotalDartPoint() == null) {
            validationException.setErrorMessage("totalDartPoint is required");
            throw validationException;
        } else if (request.getRingInType() == null) {
            validationException.setErrorMessage("ringInType is required");
            throw validationException;
        } else if (request.getRingOutType() == null) {
            validationException.setErrorMessage("ringOutType is required");
            throw validationException;
        } else if (request.getWinningRule() == null) {
            validationException.setErrorMessage("winningRule is required");
            throw validationException;
        } else if (request.getMatchType() == null) {
            validationException.setErrorMessage("matchType is required");
            throw validationException;
        } else if (request.getInputLevel() == null) {
            validationException.setErrorMessage("manageResultType is required");
            throw validationException;
        } else if (request.getBoardNo() == null || request.getBoardNo().isEmpty()) {
            validationException.setErrorMessage("boardNo is required");
            throw validationException;
        } else if (request.getVenueId() == null) {
            validationException.setErrorMessage("venueId is required");
            throw validationException;
        } else if (request.getTournamentDateTime() == null || request.getTournamentDateTime().isEmpty()) {
            validationException.setErrorMessage("Match Date Time is required");
            throw validationException;
        } else

            return true;
    }
}
