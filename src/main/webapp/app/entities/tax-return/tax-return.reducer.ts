import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITaxReturn, defaultValue } from 'app/shared/model/tax-return.model';

export const ACTION_TYPES = {
  FETCH_TAXRETURN_LIST: 'taxReturn/FETCH_TAXRETURN_LIST',
  FETCH_TAXRETURN: 'taxReturn/FETCH_TAXRETURN',
  CREATE_TAXRETURN: 'taxReturn/CREATE_TAXRETURN',
  UPDATE_TAXRETURN: 'taxReturn/UPDATE_TAXRETURN',
  DELETE_TAXRETURN: 'taxReturn/DELETE_TAXRETURN',
  RESET: 'taxReturn/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITaxReturn>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TaxReturnState = Readonly<typeof initialState>;

// Reducer

export default (state: TaxReturnState = initialState, action): TaxReturnState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TAXRETURN_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TAXRETURN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TAXRETURN):
    case REQUEST(ACTION_TYPES.UPDATE_TAXRETURN):
    case REQUEST(ACTION_TYPES.DELETE_TAXRETURN):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TAXRETURN_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TAXRETURN):
    case FAILURE(ACTION_TYPES.CREATE_TAXRETURN):
    case FAILURE(ACTION_TYPES.UPDATE_TAXRETURN):
    case FAILURE(ACTION_TYPES.DELETE_TAXRETURN):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TAXRETURN_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TAXRETURN):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TAXRETURN):
    case SUCCESS(ACTION_TYPES.UPDATE_TAXRETURN):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TAXRETURN):
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

const apiUrl = 'api/tax-returns';

// Actions

export const getEntities: ICrudGetAllAction<ITaxReturn> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TAXRETURN_LIST,
  payload: axios.get<ITaxReturn>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITaxReturn> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TAXRETURN,
    payload: axios.get<ITaxReturn>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITaxReturn> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TAXRETURN,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITaxReturn> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TAXRETURN,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITaxReturn> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TAXRETURN,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
