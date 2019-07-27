import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './business.reducer';
import { IBusiness } from 'app/shared/model/business.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBusinessUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBusinessUpdateState {
  isNew: boolean;
  clientId: string;
}

export class BusinessUpdate extends React.Component<IBusinessUpdateProps, IBusinessUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { businessEntity } = this.props;
      const entity = {
        ...businessEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/business');
  };

  render() {
    const { businessEntity, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.business.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.business.home.createOrEditLabel">Create or edit a Business</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : businessEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="business-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="business-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="businessNameLabel" for="business-businessName">
                    <Translate contentKey="accounTalkApp.business.businessName">Business Name</Translate>
                  </Label>
                  <AvField id="business-businessName" type="text" name="businessName" />
                </AvGroup>
                <AvGroup>
                  <Label id="businessDescriptionLabel" for="business-businessDescription">
                    <Translate contentKey="accounTalkApp.business.businessDescription">Business Description</Translate>
                  </Label>
                  <AvField id="business-businessDescription" type="text" name="businessDescription" />
                </AvGroup>
                <AvGroup>
                  <Label id="businessTypeLabel" for="business-businessType">
                    <Translate contentKey="accounTalkApp.business.businessType">Business Type</Translate>
                  </Label>
                  <AvInput
                    id="business-businessType"
                    type="select"
                    className="form-control"
                    name="businessType"
                    value={(!isNew && businessEntity.businessType) || 'SOLE_TRADE'}
                  >
                    <option value="SOLE_TRADE">{translate('accounTalkApp.BusinessType.SOLE_TRADE')}</option>
                    <option value="PARTNERSHIP">{translate('accounTalkApp.BusinessType.PARTNERSHIP')}</option>
                    <option value="RENTAL_PROPERTY">{translate('accounTalkApp.BusinessType.RENTAL_PROPERTY')}</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="business-client">
                    <Translate contentKey="accounTalkApp.business.client">Client</Translate>
                  </Label>
                  <AvInput id="business-client" type="select" className="form-control" name="client.id">
                    <option value="" key="0" />
                    {clients
                      ? clients.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/business" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  clients: storeState.client.entities,
  businessEntity: storeState.business.entity,
  loading: storeState.business.loading,
  updating: storeState.business.updating,
  updateSuccess: storeState.business.updateSuccess
});

const mapDispatchToProps = {
  getClients,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BusinessUpdate);
