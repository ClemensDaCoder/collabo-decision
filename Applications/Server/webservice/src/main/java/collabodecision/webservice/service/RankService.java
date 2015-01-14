package collabodecision.webservice.service;

import java.util.List;

import collabodecision.webservice.data.RequestWrapperRank;

public interface RankService {
	void addRanks(List<RequestWrapperRank> rankings);
}
