import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEmployment, defaultValue } from 'app/shared/model/employment.model';

export const ACTION_TYPES = {
  FETCH_EMPLOYMENT_LIST: 'employment/FETCH_EMPLOYMENT_LIST',
  FETCH_EMPLOYMENT: 'employment/FETCH_EMPLOYMENT',
  CREATE_EMPLOYMENT: 'employment/CREATE_EMPLOYMENT',
  UPDATE_EMPLOYMENT: 'employment/UPDATE_EMPLOYMENT',
  DELETE_EMPLOYMENT: 'employment/DELETE_EMPLOYMENT',
  RESET: 'employment/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEmployment>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EmploymentState = Readonly<typeof initialState>;

// Reducer

export default (state: EmploymentState = initialState, action): EmploymentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYMENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EMPLOYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EMPLOYMENT):
    case REQUEST(ACTION_TYPES.UPDATE_EMPLOYMENT):
    case REQUEST(ACTION_TYPES.DELETE_EMPLOYMENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYMENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EMPLOYMENT):
    case FAILURE(ACTION_TYPES.CREATE_EMPLOYMENT):
    case FAILURE(ACTION_TYPES.UPDATE_EMPLOYMENT):
    case FAILURE(ACTION_TYPES.DELETE_EMPLOYMENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYMENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EMPLOYMENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EMPLOYMENT):
    case SUCCESS(ACTION_TYPES.UPDATE_EMPLOYMENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EMPLOYMENT):
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

const apiUrl = 'api/employments';

// Actions

export const getEntities: ICrudGetAllAction<IEmployment> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EMPLOYMENT_LIST,
  payload: axios.get<IEmployment>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEmployment> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EMPLOYMENT,
    payload: axios.get<IEmployment>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEmployment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EMPLOYMENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEmployment> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EMPLOYMENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEmployment> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EMPLOYMENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
