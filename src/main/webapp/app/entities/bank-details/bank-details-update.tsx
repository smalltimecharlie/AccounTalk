import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITaxReturn } from 'app/shared/model/tax-return.model';
import { getEntities as getTaxReturns } from 'app/entities/tax-return/tax-return.reducer';
import { IClient } from 'app/shared/model/client.model';
import { getEntities as getClients } from 'app/entities/client/client.reducer';
import { getEntity, updateEntity, createEntity, reset } from './bank-details.reducer';
import { IBankDetails } from 'app/shared/model/bank-details.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBankDetailsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IBankDetailsUpdateState {
  isNew: boolean;
  taxReturnId: string;
  clientId: string;
}

export class BankDetailsUpdate extends React.Component<IBankDetailsUpdateProps, IBankDetailsUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      taxReturnId: '0',
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

    this.props.getTaxReturns();
    this.props.getClients();
  }

  saveEntity = (event, errors, values) => {
    values.openingDate = convertDateTimeToServer(values.openingDate);
    values.closedDate = convertDateTimeToServer(values.closedDate);

    if (errors.length === 0) {
      const { bankDetailsEntity } = this.props;
      const entity = {
        ...bankDetailsEntity,
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
    this.props.history.push('/entity/bank-details');
  };

  render() {
    const { bankDetailsEntity, taxReturns, clients, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="accounTalkApp.bankDetails.home.createOrEditLabel">
              <Translate contentKey="accounTalkApp.bankDetails.home.createOrEditLabel">Create or edit a BankDetails</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : bankDetailsEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="bank-details-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="bank-details-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="accountHolderNameLabel" for="bank-details-accountHolderName">
                    <Translate contentKey="accounTalkApp.bankDetails.accountHolderName">Account Holder Name</Translate>
                  </Label>
                  <AvField id="bank-details-accountHolderName" type="text" name="accountHolderName" />
                </AvGroup>
                <AvGroup>
                  <Label id="accountNumberLabel" for="bank-details-accountNumber">
                    <Translate contentKey="accounTalkApp.bankDetails.accountNumber">Account Number</Translate>
                  </Label>
                  <AvField id="bank-details-accountNumber" type="text" name="accountNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="sortCodeLabel" for="bank-details-sortCode">
                    <Translate contentKey="accounTalkApp.bankDetails.sortCode">Sort Code</Translate>
                  </Label>
                  <AvField id="bank-details-sortCode" type="text" name="sortCode" />
                </AvGroup>
                <AvGroup>
                  <Label id="jointAccountLabel" check>
                    <AvInput id="bank-details-jointAccount" type="checkbox" className="form-control" name="jointAccount" />
                    <Translate contentKey="accounTalkApp.bankDetails.jointAccount">Joint Account</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="bankNameLabel" for="bank-details-bankName">
                    <Translate contentKey="accounTalkApp.bankDetails.bankName">Bank Name</Translate>
                  </Label>
                  <AvField id="bank-details-bankName" type="text" name="bankName" />
                </AvGroup>
                <AvGroup>
                  <Label id="openingDateLabel" for="bank-details-openingDate">
                    <Translate contentKey="accounTalkApp.bankDetails.openingDate">Opening Date</Translate>
                  </Label>
                  <AvInput
                    id="bank-details-openingDate"
                    type="datetime-local"
                    className="form-control"
                    name="openingDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.bankDetailsEntity.openingDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="closedDateLabel" for="bank-details-closedDate">
                    <Translate contentKey="accounTalkApp.bankDetails.closedDate">Closed Date</Translate>
                  </Label>
                  <AvInput
                    id="bank-details-closedDate"
                    type="datetime-local"
                    className="form-control"
                    name="closedDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.bankDetailsEntity.closedDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="bank-details-client">
                    <Translate contentKey="accounTalkApp.bankDetails.client">Client</Translate>
                  </Label>
                  <AvInput id="bank-details-client" type="select" className="form-control" name="client.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/bank-details" replace color="info">
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
  taxReturns: storeState.taxReturn.entities,
  clients: storeState.client.entities,
  bankDetailsEntity: storeState.bankDetails.entity,
  loading: storeState.bankDetails.loading,
  updating: storeState.bankDetails.updating,
  updateSuccess: storeState.bankDetails.updateSuccess
});

const mapDispatchToProps = {
  getTaxReturns,
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
)(BankDetailsUpdate);
