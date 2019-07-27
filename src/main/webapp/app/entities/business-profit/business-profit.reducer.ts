import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBusinessProfit, defaultValue } from 'app/shared/model/business-profit.model';

export const ACTION_TYPES = {
  FETCH_BUSINESSPROFIT_LIST: 'businessProfit/FETCH_BUSINESSPROFIT_LIST',
  FETCH_BUSINESSPROFIT: 'businessProfit/FETCH_BUSINESSPROFIT',
  CREATE_BUSINESSPROFIT: 'businessProfit/CREATE_BUSINESSPROFIT',
  UPDATE_BUSINESSPROFIT: 'businessProfit/UPDATE_BUSINESSPROFIT',
  DELETE_BUSINESSPROFIT: 'businessProfit/DELETE_BUSINESSPROFIT',
  RESET: 'businessProfit/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBusinessProfit>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BusinessProfitState = Readonly<typeof initialState>;

// Reducer

export default (state: BusinessProfitState = initialState, action): BusinessProfitState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BUSINESSPROFIT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BUSINESSPROFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BUSINESSPROFIT):
    case REQUEST(ACTION_TYPES.UPDATE_BUSINESSPROFIT):
    case REQUEST(ACTION_TYPES.DELETE_BUSINESSPROFIT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BUSINESSPROFIT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BUSINESSPROFIT):
    case FAILURE(ACTION_TYPES.CREATE_BUSINESSPROFIT):
    case FAILURE(ACTION_TYPES.UPDATE_BUSINESSPROFIT):
    case FAILURE(ACTION_TYPES.DELETE_BUSINESSPROFIT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUSINESSPROFIT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BUSINESSPROFIT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BUSINESSPROFIT):
    case SUCCESS(ACTION_TYPES.UPDATE_BUSINESSPROFIT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BUSINESSPROFIT):
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

const apiUrl = 'api/business-profits';

// Actions

export const getEntities: ICrudGetAllAction<IBusinessProfit> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BUSINESSPROFIT_LIST,
  payload: axios.get<IBusinessProfit>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBusinessProfit> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BUSINESSPROFIT,
    payload: axios.get<IBusinessProfit>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBusinessProfit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BUSINESSPROFIT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBusinessProfit> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BUSINESSPROFIT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBusinessProfit> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BUSINESSPROFIT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
