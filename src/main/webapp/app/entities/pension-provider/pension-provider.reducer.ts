import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPensionProvider, defaultValue } from 'app/shared/model/pension-provider.model';

export const ACTION_TYPES = {
  FETCH_PENSIONPROVIDER_LIST: 'pensionProvider/FETCH_PENSIONPROVIDER_LIST',
  FETCH_PENSIONPROVIDER: 'pensionProvider/FETCH_PENSIONPROVIDER',
  CREATE_PENSIONPROVIDER: 'pensionProvider/CREATE_PENSIONPROVIDER',
  UPDATE_PENSIONPROVIDER: 'pensionProvider/UPDATE_PENSIONPROVIDER',
  DELETE_PENSIONPROVIDER: 'pensionProvider/DELETE_PENSIONPROVIDER',
  RESET: 'pensionProvider/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPensionProvider>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PensionProviderState = Readonly<typeof initialState>;

// Reducer

export default (state: PensionProviderState = initialState, action): PensionProviderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PENSIONPROVIDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PENSIONPROVIDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PENSIONPROVIDER):
    case REQUEST(ACTION_TYPES.UPDATE_PENSIONPROVIDER):
    case REQUEST(ACTION_TYPES.DELETE_PENSIONPROVIDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PENSIONPROVIDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PENSIONPROVIDER):
    case FAILURE(ACTION_TYPES.CREATE_PENSIONPROVIDER):
    case FAILURE(ACTION_TYPES.UPDATE_PENSIONPROVIDER):
    case FAILURE(ACTION_TYPES.DELETE_PENSIONPROVIDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PENSIONPROVIDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PENSIONPROVIDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PENSIONPROVIDER):
    case SUCCESS(ACTION_TYPES.UPDATE_PENSIONPROVIDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PENSIONPROVIDER):
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

const apiUrl = 'api/pension-providers';

// Actions

export const getEntities: ICrudGetAllAction<IPensionProvider> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PENSIONPROVIDER_LIST,
  payload: axios.get<IPensionProvider>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPensionProvider> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PENSIONPROVIDER,
    payload: axios.get<IPensionProvider>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPensionProvider> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PENSIONPROVIDER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPensionProvider> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PENSIONPROVIDER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPensionProvider> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PENSIONPROVIDER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
