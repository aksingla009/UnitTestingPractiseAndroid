package com.aman.unittestingpractiseandroid.java.test_driven_development.exercise7;

import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise7.networking.GetReputationHttpEndpointSync;
import com.aman.unittestingpractiseandroid.java.test_driven_development.exercise7.networking.GetReputationHttpEndpointSync.EndpointResult;

public class FetchReputationUseCaseSync {


    private GetReputationHttpEndpointSync getReputationHttpEndpointSync;

    public FetchReputationUseCaseSync(GetReputationHttpEndpointSync getReputationHttpEndpointSync) {
        this.getReputationHttpEndpointSync = getReputationHttpEndpointSync;
    }

    public enum UseResultStatus {
        SUCCESS,
        FAILURE
    }

    public static class UseResult{
        private final UseResultStatus mUseResultStatus;
        private final int mReputation;

        UseResult(UseResultStatus mUseResultStatus, int reputation) {
            this.mUseResultStatus = mUseResultStatus;
            mReputation = reputation;
        }

        public UseResultStatus getStatus() {
            return mUseResultStatus;
        }

        int getReputation() {
            return mReputation;
        }
    }


     UseResult fetchReputation() {
        EndpointResult result = getReputationHttpEndpointSync.getReputationSync();
        if(result.getStatus() == GetReputationHttpEndpointSync.EndpointStatus.SUCCESS){
            return new UseResult(UseResultStatus.SUCCESS, result.getReputation());
        }else if (result.getStatus() == GetReputationHttpEndpointSync.EndpointStatus.GENERAL_ERROR){
            return new UseResult(UseResultStatus.FAILURE, result.getReputation());
        }else if (result.getStatus() == GetReputationHttpEndpointSync.EndpointStatus.NETWORK_ERROR){
            return new UseResult(UseResultStatus.FAILURE, result.getReputation());
        }else{
            throw new RuntimeException("Invalid Status"+result);
        }


    }




}
