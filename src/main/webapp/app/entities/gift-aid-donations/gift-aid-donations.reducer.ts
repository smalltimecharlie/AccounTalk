import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IGiftAidDonations, defaultValue } from 'app/shared/model/gift-aid-donations.model';

export const ACTION_TYPES = {
  FETCH_GIFTAIDDONATIONS_LIST: 'giftAidDonations/FETCH_GIFTAIDDONATIONS_LIST',
  FETCH_GIFTAIDDONATIONS: 'giftAidDonations/FETCH_GIFTAIDDONATIONS',
  CREATE_GIFTAIDDONATIONS: 'giftAidDonations/CREATE_GIFTAIDDONATIONS',
  UPDATE_GIFTAIDDONATIONS: 'giftAidDonations/UPDATE_GIFTAIDDONATIONS',
  DELETE_GIFTAIDDONATIONS: 'giftAidDonations/DELETE_GIFTAIDDONATIONS',
  RESET: 'giftAidDonations/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IGiftAidDonations>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type GiftAidDonationsState = Readonly<typeof initialState>;

// Reducer

export default (state: GiftAidDonationsState = initialState, action): GiftAidDonationsState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_GIFTAIDDONATIONS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_GIFTAIDDONATIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_GIFTAIDDONATIONS):
    case REQUEST(ACTION_TYPES.UPDATE_GIFTAIDDONATIONS):
    case REQUEST(ACTION_TYPES.DELETE_GIFTAIDDONATIONS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_GIFTAIDDONATIONS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_GIFTAIDDONATIONS):
    case FAILURE(ACTION_TYPES.CREATE_GIFTAIDDONATIONS):
    case FAILURE(ACTION_TYPES.UPDATE_GIFTAIDDONATIONS):
    case FAILURE(ACTION_TYPES.DELETE_GIFTAIDDONATIONS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTAIDDONATIONS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_GIFTAIDDONATIONS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_GIFTAIDDONATIONS):
    case SUCCESS(ACTION_TYPES.UPDATE_GIFTAIDDONATIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_GIFTAIDDONATIONS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/gift-aid-donations';

// Actions

export const getEntities: ICrudGetAllAction<IGiftAidDonations> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_GIFTAIDDONATIONS_LIST,
  payload: axios.get<IGiftAidDonations>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IGiftAidDonations> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_GIFTAIDDONATIONS,
    payload: axios.get<IGiftAidDonations>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IGiftAidDonations> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_GIFTAIDDONATIONS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IGiftAidDonations> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_GIFTAIDDONATIONS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IGiftAidDonations> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_GIFTAIDDONATIONS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
