import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './bank-details.reducer';
import { IBankDetails } from 'app/shared/model/bank-details.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBankDetailsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BankDetailsDetail extends React.Component<IBankDetailsDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { bankDetailsEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="accounTalkApp.bankDetails.detail.title">BankDetails</Translate> [<b>{bankDetailsEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="accountHolderName">
                <Translate contentKey="accounTalkApp.bankDetails.accountHolderName">Account Holder Name</Translate>
              </span>
            </dt>
            <dd>{bankDetailsEntity.accountHolderName}</dd>
            <dt>
              <span id="accountNumber">
                <Translate contentKey="accounTalkApp.bankDetails.accountNumber">Account Number</Translate>
              </span>
            </dt>
            <dd>{bankDetailsEntity.accountNumber}</dd>
            <dt>
              <span id="sortCode">
                <Translate contentKey="accounTalkApp.bankDetails.sortCode">Sort Code</Translate>
              </span>
            </dt>
            <dd>{bankDetailsEntity.sortCode}</dd>
            <dt>
              <span id="jointAccount">
                <Translate contentKey="accounTalkApp.bankDetails.jointAccount">Joint Account</Translate>
              </span>
            </dt>
            <dd>{bankDetailsEntity.jointAccount ? 'true' : 'false'}</dd>
            <dt>
              <span id="bankName">
                <Translate contentKey="accounTalkApp.bankDetails.bankName">Bank Name</Translate>
              </span>
            </dt>
            <dd>{bankDetailsEntity.bankName}</dd>
            <dt>
              <span id="openingDate">
                <Translate contentKey="accounTalkApp.bankDetails.openingDate">Opening Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={bankDetailsEntity.openingDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="closedDate">
                <Translate contentKey="accounTalkApp.bankDetails.closedDate">Closed Date</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={bankDetailsEntity.closedDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="accounTalkApp.bankDetails.client">Client</Translate>
            </dt>
            <dd>{bankDetailsEntity.client ? bankDetailsEntity.client.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/bank-details" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/bank-details/${bankDetailsEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ bankDetails }: IRootState) => ({
  bankDetailsEntity: bankDetails.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BankDetailsDetail);
