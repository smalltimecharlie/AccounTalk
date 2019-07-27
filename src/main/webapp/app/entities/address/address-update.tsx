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
import { IPreviousAccountant } from 'app/shared/model/previous-accountant.model';
import { getEntities as getPreviousAccountants } from 'app/entities/previous-accountant/previous-accountant.reducer';
import { getEntity, updateEntity, createEntity, reset } from './address.reducer';
import { IAddress } from 'app/shared/model/address.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAddressUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAddressUpdateState {
  isNew: boolean;
  clientId: string;
  previousAccountantId: string;
}

export class AddressUpdate extends React.Component<IAddressUpdateProps, IAddressUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      clientId: '0',
      previousAccountantId: '0',
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
    this.props.getPreviousAccountants();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { addressEntity } = this.props;
      const entity = {
        ...addressEntity,
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
    this.props.history.push('/entity/address');
  };

  render() {
    const { addressEntity, clients, previousAccountants, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.address.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.address.home.createOrEditLabel">Create or edit a Address</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : addressEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="address-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="address-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="address1Label" for="address-address1">
                    <Translate contentKey="accounTalkApp.address.address1">Address 1</Translate>
                  </Label>
                  <AvField id="address-address1" type="text" name="address1" />
                </AvGroup>
                <AvGroup>
                  <Label id="address2Label" for="address-address2">
                    <Translate contentKey="accounTalkApp.address.address2">Address 2</Translate>
                  </Label>
                  <AvField id="address-address2" type="text" name="address2" />
                </AvGroup>
                <AvGroup>
                  <Label id="address3Label" for="address-address3">
                    <Translate contentKey="accounTalkApp.address.address3">Address 3</Translate>
                  </Label>
                  <AvField id="address-address3" type="text" name="address3" />
                </AvGroup>
                <AvGroup>
                  <Label id="townLabel" for="address-town">
                    <Translate contentKey="accounTalkApp.address.town">Town</Translate>
                  </Label>
                  <AvField id="address-town" type="text" name="town" />
                </AvGroup>
                <AvGroup>
                  <Label id="countyLabel" for="address-county">
                    <Translate contentKey="accounTalkApp.address.county">County</Translate>
                  </Label>
                  <AvField id="address-county" type="text" name="county" />
                </AvGroup>
                <AvGroup>
                  <Label id="countryLabel" for="address-country">
                    <Translate contentKey="accounTalkApp.address.country">Country</Translate>
                  </Label>
                  <AvField id="address-country" type="text" name="country" />
                </AvGroup>
                <AvGroup>
                  <Label id="postcodeLabel" for="address-postcode">
                    <Translate contentKey="accounTalkApp.address.postcode">Postcode</Translate>
                  </Label>
                  <AvField
                    id="address-postcode"
                    type="text"
                    name="postcode"
                    validate={{
                      maxLength: { value: 10, errorMessage: translate('entity.validation.maxlength', { max: 10 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="address-client">
                    <Translate contentKey="accounTalkApp.address.client">Client</Translate>
                  </Label>
                  <AvInput id="address-client" type="select" className="form-control" name="client.id">
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
                <AvGroup>
                  <Label for="address-previousAccountant">
                    <Translate contentKey="accounTalkApp.address.previousAccountant">Previous Accountant</Translate>
                  </Label>
                  <AvInput id="address-previousAccountant" type="select" className="form-control" name="previousAccountant.id">
                    <option value="" key="0" />
                    {previousAccountants
                      ? previousAccountants.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/address" replace color="info">
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
  previousAccountants: storeState.previousAccountant.entities,
  addressEntity: storeState.address.entity,
  loading: storeState.address.loading,
  updating: storeState.address.updating,
  updateSuccess: storeState.address.updateSuccess
});

const mapDispatchToProps = {
  getClients,
  getPreviousAccountants,
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
)(AddressUpdate);
